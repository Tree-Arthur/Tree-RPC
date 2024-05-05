package com.tree.rpc.server;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 17:36
 * @Package: com.tree.treerpc.server
 * @Description: HTTP 服务器接口
 * @version: 1.0
 */
public interface HttpServer {

    /**
     * 启动服务
     * @param port
     */
    void doStart(int port);

}
