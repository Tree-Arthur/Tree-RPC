package com.tree.example.common.service;

import com.tree.example.common.model.User;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 17:05
 * @Package: com.tree.example.common.service
 * @Description: 用户服务接口
 * @version: 1.0
 */
public interface UserService {
    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 新方法 - 获取数字
     */
    default short getNumber() {
        return 1;
    }
}
