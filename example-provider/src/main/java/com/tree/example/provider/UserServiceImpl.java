package com.tree.example.provider;

import com.tree.example.common.model.User;
import com.tree.example.common.service.UserService;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 17:12
 * @Package: com.tree.example.provider
 * @Description: 服务实现类
 * @version: 1.0
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
