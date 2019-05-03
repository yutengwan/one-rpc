/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.yuteng.rpc.netty;

import com.yuteng.rpc.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 *
 * @author wanyuteng
 * @version $Id: NettyClient.java, v 0.1 2019年05月03日 4:54 PM wanyuteng Exp $
 */
public class NettyClient {

    public void connect(int port, String host) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 初始化，将handler设置在channelPiple中
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });

            // 异步连接操作
            ChannelFuture cf = bootstrap.connect(host, port).sync();

            // 客户端链路关闭
            cf.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new NettyClient().connect(8088, "127.0.0.1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}