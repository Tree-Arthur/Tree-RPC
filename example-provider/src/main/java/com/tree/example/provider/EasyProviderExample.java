package com.tree.example.provider;

import com.tree.example.common.service.UserService;
import com.tree.rpc.registry.LocalRegistry;
import com.tree.rpc.server.HttpServer;
import com.tree.rpc.server.VertxHttpServer;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 17:13
 * @Package: com.tree.example.provider
 * @Description: 简易服务提供者示例
 * @version: 1.0
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        //注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        //启动web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
