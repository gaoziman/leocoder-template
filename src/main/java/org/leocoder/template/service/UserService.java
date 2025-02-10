package org.leocoder.template.service;

import org.leocoder.template.domain.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author : 程序员Leo
 * @version 1.0
 * @date 2025-02-10 15:36
 * @description :
 */

public interface UserService extends IService<User> {

    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    String encryptPassword(String rawPassword);
}
