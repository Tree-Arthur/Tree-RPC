package com.tree.rpc.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tree.rpc.model.RpcRequest;
import com.tree.rpc.model.RpcResponse;

import java.io.IOException;

/**
 * @author : Tree Arthur
 * @date: 2024/5/10 16:43
 * @Package: com.tree.rpc.serializer
 * @Description: Json 序列化器
 * @version: 1.0
 */
public class JsonSerializer implements Serializer {
    public static final ObjectMapper OBJECT_MAPPER =  new ObjectMapper();
    @Override
    // 将对象序列化为字节数组
    public <T> byte[] serialize(T obj) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(obj);
    }

    @Override
    // 将字节数组反序列化为对象
    public <T> T deserialize(byte[] data, Class<T> classType) throws IOException {
        T obj = OBJECT_MAPPER.readValue(data, classType);
        // 如果是RpcRequest类型，则调用handleRequest方法处理
        if (obj instanceof RpcRequest){
            return handleRequest((RpcRequest)obj,classType);
        }
        // 如果是RpcResponse类型，则调用handleResponse方法处理
        if (obj instanceof RpcResponse){
            return handleResponse((RpcResponse)obj,classType);
        }
        // 其他类型直接返回
        return obj;
    }


    /**
     * 由于 Object 的原始对象会被擦除，导致反序列化时会被作为 LinkedHashMap 无法转换成原始对象，因此这里做了特殊处理
     * @param rpcRequest rpc 请求
     * @param type 类型
     * @return
     * @param <T>
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        //获取参数类型
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        //获取参数
        Object[] args = rpcRequest.getArgs();
        //循环处理每个参数类型
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            //判断参数类型是否可以被参数args[i]所继承
            if (!clazz.isAssignableFrom(args[i].getClass())){
                //将参数args[i]转换为字节数组
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                //从字节数组中读取参数，并转换为clazz类型
                args[i] = OBJECT_MAPPER.readValue(argBytes, clazz);
            }
        }
        //返回类型为type的参数
        return type.cast(rpcRequest);
    }

    /**
     * 由于 Object 的原始对象会被擦除，导致反序列化时会被作为 LinkedHashMap 无法转换成原始对象，因此这里做了特殊处理
     * @param rpcResponse rpc 响应
     * @param type 类型
     * @return
     * @param <T>
     */
    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        //处理响应数据
        //将响应数据序列化为字节数组
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        //从字节数组中读取响应数据
        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        //返回类型为T的对象
        return type.cast(rpcResponse);
    }
}

