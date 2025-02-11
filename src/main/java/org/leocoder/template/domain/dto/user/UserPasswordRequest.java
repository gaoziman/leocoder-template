package org.leocoder.template.domain.dto.user;

import lombok.Data;

/**
 * @author : 程序员Leo
 * @version 1.0
 * @date 2025-02-10 22:27
 * @description : 用户密码请求对象
 */
@Data
public class UserPasswordRequest {
    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认新密码
     */
    private String checkPassword;
}
