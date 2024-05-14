package com.tree.rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author : Tree Arthur
 * @date: 2024/5/10 16:58
 * @Package: com.tree.rpc.serializer
 * @Description: Kryo 序列化器
 * @version: 1.0
 */
public class KryoSerializer implements Serializer{
    //kryo 线程不安全，使用 ThreadLocal 保证每个线程只有一个 Kryo
    public static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(()->{
        //创建Kryo实例
        Kryo kryo = new Kryo();
        //设置动态序列化和反序列化类，不提前注册所有类
        kryo.setRegistrationRequired(false);
        //返回Kryo实例
        return kryo;
    });

    @Override
    //实现序列化方法
    public <T> byte[] serialize(T obj) throws IOException {
        //创建ByteArrayOutputStream对象，用于输出序列化后的数据
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //使用Output对象，将序列化后的数据输出到byteArrayOutputStream中
        Output output = new Output(byteArrayOutputStream);
        //将obj对象序列化
        KRYO_THREAD_LOCAL.get().writeObject(output, obj);
        //关闭Output对象
        output.close();
        //返回序列化后的数据
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> classType) throws IOException {
        // 将字节数组转换为ByteArrayInputStream
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        // 创建Input对象
        Input input = new Input(byteArrayInputStream);
        // 通过KRYO_THREAD_LOCAL获取序列化器，并从输入流中读取对象
        T result = KRYO_THREAD_LOCAL.get().readObject(input, classType);
        // 关闭输入流
        input.close();
        // 返回读取到的对象
        return result;
    }
}
