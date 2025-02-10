package org.leocoder.template.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.leocoder.template.common.Result;
import org.leocoder.template.common.ResultUtils;
import org.leocoder.template.domain.dto.UserAddRequest;
import org.leocoder.template.domain.pojo.User;
import org.leocoder.template.exception.ErrorCode;
import org.leocoder.template.exception.ThrowUtils;
import org.leocoder.template.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

