package com.tree.rpc.serializer;

import com.tree.rpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Tree Arthur
 * @date: 2024/5/10 17:08
 * @Package: com.tree.rpc.serializer
 * @Description: 序列化器工厂（用于获取序列化器对象）
 * @version: 1.0
 */
public class SerializerFactory {
    /**
     * 序列化映射（用于实现单例）
     */
//    public static final Map<String,Serializer> KEY_SERIALIZER_MAP = new HashMap<String,Serializer>() {
//        {
//            put(SerializerKeys.JDK,new JdkSerializer());
//            put(SerializerKeys.JSON,new JsonSerializer());
//            put(SerializerKeys.KRYO,new HessianSerializer());
//            put(SerializerKeys.HESSIAN,new KryoSerializer());
//        }
//    };

    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
//    public static final Serializer DEFAULT_SERIALIZER = KEY_SERIALIZER_MAP.get("jdk");
    public static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取序列化器对象
     * @param key
     * @return
     */
    public static Serializer getInstance(String key){
//        return KEY_SERIALIZER_MAP.getOrDefault(key,DEFAULT_SERIALIZER);
        return SpiLoader.getInstance(Serializer.class, key);
    }
}
