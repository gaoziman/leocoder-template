package org.leocoder.template.service.impl;

import cn.hutool.core.util.StrUtil;
import org.leocoder.template.exception.BusinessException;
import org.leocoder.template.exception.ErrorCode;
import org.leocoder.template.utils.PasswordUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.leocoder.template.mapper.UserMapper;
import org.leocoder.template.domain.pojo.User;
import org.leocoder.template.service.UserService;
import org.springframework.util.DigestUtils;

/**
 * @author : 程序员Leo
 * @date  2025-02-10 15:36
 * @version 1.0
 * @description :
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{


    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    @Override
    public String encryptPassword(String rawPassword) {
        String salt = PasswordUtil.generateFixedSalt("leocoder");
        if (StrUtil.isBlank(rawPassword) || StrUtil.isBlank(salt)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和盐值不能为空");
        }
        // 将密码和盐值拼接
        String saltedPassword = rawPassword + salt;
        // 使用 MD5 进行加密
        return DigestUtils.md5DigestAsHex(saltedPassword.getBytes());
    }
}
