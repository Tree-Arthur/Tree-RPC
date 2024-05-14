package com.tree.rpc.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author : Tree Arthur
 * @date: 2024/5/10 16:58
 * @Package: com.tree.rpc.serializer
 * @Description: Hessian 序列化器
 * @version: 1.0
 */
public class HessianSerializer implements Serializer{
    @Override
    //序列化对象
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(byteArrayOutputStream);
        hessianOutput.writeObject(obj);//序列化对象
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    //反序列化
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        HessianInput hessianInput = new HessianInput(byteArrayInputStream);
        return (T) hessianInput.readObject(type);
    }
}
