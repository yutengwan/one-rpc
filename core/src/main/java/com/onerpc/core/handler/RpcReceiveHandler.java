
package com.onerpc.core.handler;

import com.alibaba.fastjson.JSON;
import com.onerpc.core.core.RegisterServices;
import com.onerpc.core.exception.ServiceNotExistException;
import com.onerpc.facade.model.RpcRequest;
import com.onerpc.facade.model.RpcResponse;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 服务端handler
 *
 * @version $Id: RpcReceiveHandler.java, v 0.1 2019年05月09日 11:18 AM Exp $
 */
public class RpcReceiveHandler extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RpcReceiveHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("receive msg={}", JSON.toJSONString(msg));
        RpcRequest request = (RpcRequest) msg;
        RpcResponse rpcResponse = new RpcResponse();
        Object result = doHandler(request);
        rpcResponse.setRequestId(request.getMessageId());
        rpcResponse.setResult(result);
        ctx.writeAndFlush(rpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private Object doHandler(RpcRequest rpcRequest) throws Exception {
        Object object = RegisterServices.getInstance().getService(rpcRequest.getServiceName());
        if (object == null) {
            throw new ServiceNotExistException(rpcRequest.getServiceName() + " service not exist");
        }

        Class<?> beanClass = object.getClass();
        Method method = beanClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getTypeParameters());

        Object result = method.invoke(object, rpcRequest.getParametersVal());
        return result;
    }
}