
package com.onerpc.core.core;

import com.google.common.reflect.AbstractInvocationHandler;
import com.onerpc.core.handler.RpcSendHandler;
import com.onerpc.core.util.LoggerHelper;
import com.onerpc.facade.model.RpcRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 代理类，对象生成时，使用代理类，远程调用
 *
 * @version $Id: RpcSendProxy.java, v 0.1 2019年05月07日 5:41 PM  Exp $
 */
public class RpcSendProxy extends AbstractInvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcSendProxy.class);

    private Class<?> clazz;

    /**
     * rpc server端发布的服务名
     */
    private String serviceName;

    /**
     * 请求超时时间
     */
    private String timeout;

    /**
     * 序列号协议
     */
    private String protocol;

    public RpcSendProxy(Class<?> clazz, String serviceName, String timeout, String protocol) {
        this.clazz = clazz;
        this.serviceName = serviceName;
        this.timeout = timeout;
        this.protocol = protocol;
    }

    @Override
    protected Object handleInvocation(Object o, Method method, Object[] objects) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setServiceName(serviceName);
        request.setMethodName(method.getName());
        request.setTypeParameters(method.getParameterTypes());
        request.setParametersVal(objects);
        request.setProtocol(protocol);

        RpcSendHandler sendHandler = RpcServerLoader.getInstance().getNettyServerHandler();
        RpcCallback rpcCallback = sendHandler.sendMessage(request, timeout);

        // 等待服务端返回，同步接口，需要等待服务端调用返回之后
        try {
            return rpcCallback.waitResponse();
        } catch (Exception e) {
            LoggerHelper.error(logger, "handle invocate exception", e);
            return null;
        }
    }
}