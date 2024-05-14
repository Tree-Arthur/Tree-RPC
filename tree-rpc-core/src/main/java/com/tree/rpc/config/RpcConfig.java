package com.tree.rpc.config;

import com.tree.rpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * @author : Tree Arthur
 * @date: 2024/5/5 18:05
 * @Package: com.tree.rpc.config
 * @Description: RPC 框架配置
 * @version: 1.0
 */
@Data
public class RpcConfig {
    /**
     * RPC 框架名称
     */
    private String name="tree-rpc";

    /**
     * RPC 框架版本
     */
    private String version="1.0";

    /**
     * 服务器主机名
     */
    private String serverHost="localhost";

    /**
     * 服务器端口
     */
    private int serverPort=8080;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.JDK;
}
