package com.tree.rpc.server;

import io.vertx.core.Vertx;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 17:40
 * @Package: com.tree.treerpc.server
 * @Description: TODO
 * @version: 1.0
 */
public class VertxHttpServer implements HttpServer {
    @Override
    public void doStart(int port) {
        //创建Vert实例
        Vertx vertx = Vertx.vertx();
        //创建Http服务器
        io.vertx.core.http.HttpServer httpServer = vertx.createHttpServer();
        //监听端口并处理请求
        httpServer.requestHandler(new HttpServerHandler());
//        httpServer.requestHandler(httpServerRequest -> {
//            //处理Http请求
//            System.out.println("Received request: "+httpServerRequest.method()+", "+httpServerRequest.uri());
//            //发送Http响应
//            httpServerRequest.response()
//                    .putHeader("content-type", "text/plain")
//                    .end("Hello from Vert.x HTTP server!");
//        });
        //启动HTTP服务并监听指定端口
        httpServer.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("HTTP server started on port " + port);
            } else {
                System.err.println("Failed to start HTTP server: " + result.cause().getMessage());
            }
        });
    }
}
