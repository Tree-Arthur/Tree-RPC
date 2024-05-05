package com.tree.rpc.serializer;

import java.io.*;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 18:58
 * @Package: com.tree.treerpc
 * @Description: JDK 序列化器
 * @version: 1.0
 */
public class JdkSerializer implements Serializer{
    /**
     * 序列化
     * @param obj
     * @return
     * @param <T>
     * @throws IOException
     */
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(obj);
//        objectOutputStream.flush();
        objectOutputStream.close();//关闭输出流
        return outputStream.toByteArray();
    }

    /**
     * 反序列化
     * @param bytes
     * @param type
     * @return
     * @param <T>
     * @throws IOException
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        try {
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            objectInputStream.close();//关闭输入流
        }
    }
}
