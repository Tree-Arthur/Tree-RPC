package com.tree.rpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @author : Tree Arthur
 * @date: 2024/5/5 18:08
 * @Package: com.tree.rpc.utils
 * @Description: 配置工具类
 * @version: 1.0
 */
public class ConfigUtils {
    public static final String PROPERTIES = "properties";

    /**
     * 加载配置对象
     *
     * @param tClass RpcConfig.class
     * @param prefix rpc
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "","");
    }

    /**
     * application.properties application-prod.properties
     * 从配置文件中加载指定的Class<?>类型的配置信息，配置文件名根据环境变量进行配置
     *
     * @param tClass 目标Class<?>类型 RpcConfig.class
     * @param prefix rpc
     * @param env    环境变量，用于指定配置文件名
     * @return 配置信息
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String env) {
        // 创建配置文件名
        StringBuilder configFileBuilder = new StringBuilder("application");
        // 判断环境变量是否为空
        if (StrUtil.isNotBlank(env)) {
            // 如果环境变量不为空，则添加到配置文件名中
            configFileBuilder.append("-").append(env);
        }
        // 配置文件名默认为application.properties

//        configFileBuilder.append(".properties");
//        configFileBuilder.append(PROPERTIES);
        configFileBuilder.append(".yaml");
        Props props = new Props(configFileBuilder.toString());
        //props.autoLoad() 可以实现配置文件变更的监听和自动加载
        // 根据目标Class<?>类型和前缀加载配置信息
        return props.toBean(tClass, prefix);
    }

    /**
     * application.properties application-prod.properties
     * 从配置文件中加载指定的Class<?>类型的配置信息，配置文件名根据环境变量进行配置
     *
     * @param tClass        目标Class<?>类型 RpcConfig.class
     * @param prefix        rpc
     * @param env           环境变量，用于指定配置文件名
     * @param extensionName 文件扩展名，用于指定配置文件名
     * @return 配置信息
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String env, String extensionName) {
        // 创建配置文件名
        StringBuilder configFileBuilder = new StringBuilder("application");
        // 判断环境变量是否为空
        if (StrUtil.isNotBlank(env)) {
            // 如果环境变量不为空，则添加到配置文件名中
            configFileBuilder.append("-").append(env);
        }
        configFileBuilder.append(".");
        // 判断文件扩展名是否为空
        if (StrUtil.isNotBlank(extensionName)) {
            // 如果环境变量不为空，则添加到配置文件名中
            configFileBuilder.append(extensionName);
        } else {
            // 配置文件名默认为application.properties
            configFileBuilder.append(PROPERTIES);
        }
        Props props = new Props(configFileBuilder.toString());
        //props.autoLoad() 可以实现配置文件变更的监听和自动加载

        // 根据目标Class<?>类型和前缀加载配置信息
        return props.toBean(tClass, prefix);
    }
}
