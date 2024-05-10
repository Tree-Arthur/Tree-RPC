package com.tree.example.consumer;

import com.tree.example.common.model.User;
import com.tree.example.common.service.UserService;
import com.tree.rpc.config.RpcConfig;
import com.tree.rpc.proxy.ServiceProxyFactory;
import com.tree.rpc.utils.ConfigUtils;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 17:21
 * @Package: com.tree.example.consumer
 * @Description: 服务消费者示例
 * @version: 1.0
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // todo 需要获取 UserService 的实现类对象
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc","","");
        System.out.println(rpcConfig);

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
        long number = userService.getNumber();
        System.out.println(number);
    }
}
