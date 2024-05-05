package com.tree.rpc;

import com.tree.rpc.config.RpcConfig;
import com.tree.rpc.constant.RpcConstant;
import com.tree.rpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : Tree Arthur
 * @date: 2024/5/5 18:19
 * @Package: com.tree.rpc
 * @Description: RPC 框架应用 相当于 holder，存放了项目全局用到的变量。双检锁单例模式实现
 * @version: 1.0
 */
@Slf4j
public class RpcApplication {
    /**
     * RPC 配置
     */
    private static volatile RpcConfig rpcConfig;

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            //配置加载失败，使用默认配置
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 框架初始化，支持传入自定义配置
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("RPC init ,config = {}", rpcConfig.toString());
    }

    /**
     * 获取 RPC 配置
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
