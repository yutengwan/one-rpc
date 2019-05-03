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
 * @version $Id: NettyClient.java, v 0.1 2019��05��03�� 4:54 PM wanyuteng Exp $
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
                            // ��ʼ������handler������channelPiple��
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });

            // �첽���Ӳ���
            ChannelFuture cf = bootstrap.connect(host, port).sync();

            // �ͻ�����·�ر�
            cf.channel().closeFuture().sync();
        } finally {
            // �����˳����ͷ�NIO�߳���
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