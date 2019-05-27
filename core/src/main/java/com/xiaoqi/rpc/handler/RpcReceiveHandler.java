
package com.xiaoqi.rpc.handler;

import com.xiaoqi.rpc.core.RegisterServices;
import com.xiaoqi.rpc.exception.ServiceNotExistException;
import com.xiaoqi.rpc.model.RpcRequest;
import com.xiaoqi.rpc.model.RpcResponse;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;

/**
 * 服务端handler
 *
 * @version $Id: RpcReceiveHandler.java, v 0.1 2019年05月09日 11:18 AM Exp $
 */
public class RpcReceiveHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
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
        Object object = RegisterServices.getInstance().getService(rpcRequest.getClassName());
        if (object == null) {
            throw new ServiceNotExistException(rpcRequest.getClassName() + " service not exist");
        }

        Class<?> beanClass = object.getClass();
        Method method = beanClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getTypeParameters());

        Object result = method.invoke(object, rpcRequest.getParametersVal());
        return result;
    }
}