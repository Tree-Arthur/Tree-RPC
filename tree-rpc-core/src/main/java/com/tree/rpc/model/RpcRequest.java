package com.tree.rpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Tree Arthur
 * @date: 2024/5/4 19:05
 * @Package: com.tree.rpc.model
 * @Description: 请求封装类
 * @version: 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {
    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 参数类型列表
     */

    private Class<?>[] parameterTypes;

    /**
     * 参数列表
     */
    private Object[] args;
}
