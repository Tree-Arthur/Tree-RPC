package com.tree.rpc.proxy;

import com.tree.rpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 19:45
 * @Package: com.tree.rpc.proxy
 * @Description: 服务代理工厂（用于创建代理对象）
 * @version: 1.0
 */
public class ServiceProxyFactory {
    /**
     * 根据服务获取代理对象
     * @param serviceClass 服务类
     * @return 代理
     */
    public static <T> T getProxy(Class<T> serviceClass){
        // 如果配置为mock模式
        if (RpcApplication.getRpcConfig().isMock()){
            // 返回mock代理
            return getMockProxy(serviceClass);
        }
        // 返回代理实例
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass},
                new ServiceProxy());
    }

    /**
     * 根据服务类获取mock代理对象
     * @param serviceClass
     * @return
     * @param <T>
     */
    private static <T> T getMockProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass},
                new MockServiceProxy());
    }
}
