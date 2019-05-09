
package com.xiaoqi.rpc.server;

import com.xiaoqi.rpc.handler.RpcSendHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author
 * @version $Id: RpcServer.java, v 0.1 2019��04��30�� 5:25 PM  Exp $
 */
public class RpcServer {

    public void startServer(int port) throws Exception {
        // ���÷���˵�NIO�߳��飬�����¼�����
        // �����¼�����
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // ����NIO����˵ĸ���������
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //��������channel��ΪServerSocketChannel
            serverBootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RpcSendHandler());
                        }
                    });

            // �󶨶˿ڣ�ͬ���ȴ��ɹ�
            ChannelFuture cf = serverBootstrap.bind(port).sync();

            cf.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new RpcServer().startServer(8088);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}