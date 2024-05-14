package com.tree.rpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.tree.rpc.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Tree Arthur
 * @date: 2024/5/10 17:51
 * @Package: com.tree.rpc.spi
 * @Description: SPI 加载器（支持键值对映射）
 * @version: 1.0
 */
@Slf4j
public class SpiLoader {
    /**
     * 存储已加载类：接口名 => （key => 实现类)
     */
    private static Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    /**
     * 对象实例缓存（避免重复 new ），类路径 => 对象实例,单例模式
     */
    private static Map<String, Object> instanceCache = new ConcurrentHashMap<>();

    /**
     * 系统SPI目录
     */
    private static final String RPC_SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    /**
     * 用户自定义SPI目录
     */
    private static final String RPC_USER_SPI_DIR = "META-INF/rpc/custom/";

    /**
     * 扫描路径
     */
    private static final String[] SCAN_DIRS = new String[]{RPC_SYSTEM_SPI_DIR, RPC_USER_SPI_DIR};

    /**
     * 动态加载的类列表
     */
    private static final List<Class<?>> LOAD_CLASS_LIST = Arrays.asList(Serializer.class);


    /**
     * 加载所有类型
     */
    public static void loadAll() {
        log.info("加载所有 spi");
        for (Class<?> clazz : LOAD_CLASS_LIST) {
            load(clazz);
        }
    }

    /**
     * 获取某个接口的实例
     * @param tClass
     * @param key
     * @return
     * @param <T>
     */
    public static <T> T getInstance(Class<T> tClass, String key) {
        String tClassName = tClass.getName();
        Map<String,Class<?>> keyClassMap = loaderMap.get(tClassName);
        if (keyClassMap == null) {
            throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型",tClassName));
        }
        if (!keyClassMap.containsKey(key)) {
            throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key=%s 的类型",tClassName,key));
        }
        // 获取到要加载的实现类型
        Class<?> implClass = keyClassMap.get(key);

        // 从实例缓存中加载指定类型的实例
        // 获取 implClass 的名字
        String implClassName = implClass.getName();
        // 如果 instanceCache 中不存在 implClassName 的键
        if (!instanceCache.containsKey(implClassName)){
            try {
                // 将 implClass 的新实例放入 instanceCache 中
                instanceCache.put(implClassName,implClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                // 抛出 RuntimeException，包含错误信息
                String errorMsg = String.format("%s 类实例化失败",implClassName);
                throw new RuntimeException(errorMsg,e);
            }
        }
        // 返回实例 获取 instanceCache 中指定类型的实例
        return (T) instanceCache.get(implClassName);
    }

    /**
     * 加载某个类型
     * @param loadClass
     * @return
     */
    public static Map<String, Class<?>> load(Class<?> loadClass) {
        log.info("加载类型为{} 的SPI", loadClass.getName());
        //扫描路径，用户自定义的SPI优先级高于系统SPI
        HashMap<String, Class<?>> keyClassMap = new HashMap<>();
        for (String scanDir : SCAN_DIRS) {
            List<URL> resources = ResourceUtil.getResources(scanDir + loadClass.getName());
            //读取每一个资源文件
            for (URL resource : resources) {
                try {
                    //读取文件内容
                    InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] strArray = line.split("=");//按=分割
                        if (strArray.length > 1) {
                            String key = strArray[0];//获取key
                            String className = strArray[1];//获取className
                            keyClassMap.put(key, Class.forName(className));//插入Map
                        }
                    }
                } catch (Exception e) {
                    log.error("加载SPI失败", e);
                }
            }
        }
        loaderMap.put(loadClass.getName(), keyClassMap);
        return keyClassMap;
    }
}
