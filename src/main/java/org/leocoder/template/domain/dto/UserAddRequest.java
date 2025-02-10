package org.leocoder.template.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : 程序员Leo
 * @version 1.0
 * @date 2025-02-10 22:14
 * @description : 用户添加请求参数
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}