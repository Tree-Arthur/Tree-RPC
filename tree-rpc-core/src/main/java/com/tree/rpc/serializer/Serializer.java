package com.tree.rpc.serializer;

import java.io.IOException;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 18:56
 * @Package: com.tree.treerpc
 * @Description: 序列化器接口
 * @version: 1.0
 */
public interface Serializer {
    /**
     * 序列化
     * @param obj
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> byte[] serialize(T obj)throws IOException;

    /**
     * 反序列化
     * @param bytes
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> T deserialize(byte[] bytes,Class<T> type)throws IOException;
}
