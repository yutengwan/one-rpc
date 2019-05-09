
package com.xiaoqi.rpc.handler;

import com.xiaoqi.rpc.core.RpcCallback;
import com.xiaoqi.rpc.model.MessageRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
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
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("The server receive order:" + body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
                System.currentTimeMillis()).toString() : "BAD ORDER";
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.write(resp);
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

    public RpcCallback sendMessage(MessageRequest request) {
        RpcCallback rpcCallback = new RpcCallback(request);
        rpcCallbackConcurrentHashMap.put(request.getMessageId(), rpcCallback);
        channel.writeAndFlush(request);
        return rpcCallback;
    }
}