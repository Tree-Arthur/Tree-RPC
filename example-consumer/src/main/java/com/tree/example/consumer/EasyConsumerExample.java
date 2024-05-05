package com.tree.example.consumer;

import com.tree.example.common.model.User;
import com.tree.example.common.service.UserService;
import com.tree.rpc.proxy.ServiceProxyFactory;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 17:21
 * @Package: com.tree.example.consumer
 * @Description: 简易服务消费者示例
 * @version: 1.0
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // todo 需要获取 UserService 的实现类对象

        // 动态代理获取对象
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("tree");
        User newUser = userService.getUser(user);
        if (newUser != null){
            System.out.println(newUser.getName());
        }else{
            System.out.println("user == null");
        }
    }
}
