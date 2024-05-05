package com.tree.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.tree.example.common.model.User;
import com.tree.example.common.service.UserService;
import com.tree.rpc.model.RpcRequest;
import com.tree.rpc.model.RpcResponse;
import com.tree.rpc.serializer.JdkSerializer;
import com.tree.rpc.serializer.Serializer;

import java.io.IOException;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 19:31
 * @Package: com.tree.example.consumer
 * @Description: 静态代理
 * @version: 1.0
 */
public class UserServiceProxy implements UserService {

    @Override
    public User getUser(User user) {
        //指定序列化器
        Serializer serializer = new JdkSerializer();
        //构造请求
        RpcRequest rpcRequest = RpcRequest.builder().serviceName(UserService.class.getName())
                .methodName("getUser")
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bytes = serializer.serialize(rpcRequest);
            byte[] result;
            //发送请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            //反序列化
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
