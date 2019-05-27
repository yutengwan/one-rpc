
package com.xiaoqi.rpc.handler;

import com.xiaoqi.rpc.core.RpcCallback;
import com.xiaoqi.rpc.model.RpcRequest;
import com.xiaoqi.rpc.model.RpcResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 发送端handler
 *
 * @version $Id: NettySendHandler.java, v 0.1 2019年05月03日 4:37 PM  Exp $
 */
public class RpcSendHandler extends ChannelHandlerAdapter {

    private ConcurrentHashMap<String, RpcCallback> rpcCallbackConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 消息发送管道
     */
    private volatile Channel channel;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse response = (RpcResponse) msg;
        String requestId = response.getRequestId();
        RpcCallback rpcCallback = rpcCallbackConcurrentHashMap.get(requestId);
        if (rpcCallback != null) {
            rpcCallbackConcurrentHashMap.remove(requestId);
            rpcCallback.getResponse(response);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        channel = ctx.channel();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public RpcCallback sendMessage(RpcRequest request) {
        RpcCallback rpcCallback = new RpcCallback(request);
        rpcCallbackConcurrentHashMap.put(request.getMessageId(), rpcCallback);
        channel.writeAndFlush(request);
        return rpcCallback;
    }
}