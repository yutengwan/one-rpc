
package com.onerpc.core.core;

import com.google.common.reflect.AbstractInvocationHandler;
import com.onerpc.core.handler.RpcSendHandler;
import com.onerpc.core.model.RpcRequest;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 代理类，对象生成时，使用代理类，远程调用
 *
 * @version $Id: RpcSendProxy.java, v 0.1 2019年05月07日 5:41 PM  Exp $
 */
public class RpcSendProxy extends AbstractInvocationHandler {

    private Class<?> clazz;

    public RpcSendProxy(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected Object handleInvocation(Object o, Method method, Object[] objects) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(clazz.getSimpleName());
        request.setMethodName(method.getName());
        request.setTypeParameters(method.getParameterTypes());
        request.setParametersVal(objects);

        RpcSendHandler sendHandler = RpcServerLoader.getInstance().getNettyServerHandler();
        RpcCallback rpcCallback = sendHandler.sendMessage(request);

        // 等待服务端返回，同步接口，需要等待服务端调用返回之后
        return rpcCallback.waitResponse();
    }
}