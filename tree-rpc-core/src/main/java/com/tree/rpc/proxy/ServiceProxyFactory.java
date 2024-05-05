package com.tree.rpc.proxy;

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
     * @param serviceClass
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass},
                new ServiceProxy());
    }
}
