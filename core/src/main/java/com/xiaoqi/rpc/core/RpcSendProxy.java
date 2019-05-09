
package com.xiaoqi.rpc.core;

import com.google.common.reflect.AbstractInvocationHandler;
import com.xiaoqi.rpc.handler.RpcSendHandler;
import com.xiaoqi.rpc.model.MessageRequest;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * �����࣬��������ʱ��ʹ�ô����࣬Զ�̵���
 *
 * @version $Id: RpcSendProxy.java, v 0.1 2019��05��07�� 5:41 PM  Exp $
 */
public class RpcSendProxy extends AbstractInvocationHandler {

    @Override
    protected Object handleInvocation(Object o, Method method, Object[] objects) throws Throwable {
        MessageRequest request = new MessageRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setTypeParameters(method.getParameterTypes());
        request.setParametersVal(objects);

        RpcSendHandler sendHandler = RpcServerLoader.getInstance().getNettyServerHandler();
        RpcCallback rpcCallback = sendHandler.sendMessage(request);

        // �ȴ�����˷��أ�ͬ���ӿڣ���Ҫ�ȴ�����˵��÷���֮��
        return rpcCallback.waitResponse();
    }
}