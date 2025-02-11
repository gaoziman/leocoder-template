package org.leocoder.template.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.leocoder.template.annotation.AuthCheck;
import org.leocoder.template.common.DeleteRequest;
import org.leocoder.template.common.Result;
import org.leocoder.template.common.ResultUtils;
import org.leocoder.template.constant.UserConstant;
import org.leocoder.template.domain.dto.user.UserAddRequest;
import org.leocoder.template.domain.dto.user.UserQueryRequest;
import org.leocoder.template.domain.dto.user.UserUpdateRequest;
import org.leocoder.template.domain.pojo.User;
import org.leocoder.template.domain.vo.user.UserVO;
import org.leocoder.template.exception.BusinessException;
import org.leocoder.template.exception.ErrorCode;
import org.leocoder.template.exception.ThrowUtils;
import org.leocoder.template.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.leocoder.template.constant.UserConstant.USER_DEFAULT_PASSWORD;

/**
 * @author : 程序员Leo
 * @version 1.0
 * @date 2025-02-10 22:14
 * @description :
 */
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@Api(tags = "用户管理")
public class AdminUserController {


    private final UserService userService;


    @ApiOperation(value = "创建用户")
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Long> addUser(@RequestBody UserAddRequest requestParam) {
        ThrowUtils.throwIf(ObjectUtil.isNull(requestParam), ErrorCode.PARAMS_ERROR);
        String userAccount = requestParam.getUserAccount();
        User one = userService.getOne(new LambdaQueryWrapper<>(User.class)
                .eq(User::getUserAccount, userAccount));
        ThrowUtils.throwIf(ObjectUtil.isNotNull(one), ErrorCode.BUSINESS_ERROR, "用户账号已经存在");
        User user = User.builder().build();
        BeanUtils.copyProperties(requestParam, user);
        String encryptPassword = userService.encryptPassword(USER_DEFAULT_PASSWORD);
        user.setUserPassword(encryptPassword);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }


    @ApiOperation(value = "获取用户（仅管理员）")
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<User> getUserById(Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(ObjectUtil.isNull(user), ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }


    @ApiOperation(value = "根据id获取脱敏用户信息")
    @GetMapping("/get/vo")
    public Result<UserVO> getUserVOById(Long id) {
        ThrowUtils.throwIf(id == null || id <= 0, ErrorCode.PARAMS_ERROR);
        Result<User> response = getUserById(id);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }


    @ApiOperation(value = "删除用户")
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        // 参数校验
        if (ObjectUtil.isNull(deleteRequest) || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 检查用户是否存在
        User user = userService.getById(deleteRequest.getId());
        if (ObjectUtil.isNull(user)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "用户不存在");
        }

        boolean result = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    @ApiOperation(value = "更新用户")
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (ObjectUtil.isNull(userUpdateRequest) || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = User.builder().build();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }



    @ApiOperation(value = "分页获取用户封装列表（仅管理员）")
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Result<Page<UserVO>> listUserVOByPage(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(ObjectUtil.isNull(userQueryRequest), ErrorCode.PARAMS_ERROR);
        long pageNum = userQueryRequest.getPageNum();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(pageNum, pageSize),
                userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(pageNum, pageSize, userPage.getTotal());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }
}

