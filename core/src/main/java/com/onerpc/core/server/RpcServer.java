
package com.onerpc.core.server;

import com.onerpc.core.handler.RpcReceiveHandler;
import com.onerpc.core.serialize.MessageDecoder;
import com.onerpc.core.serialize.MessageEncoder;
import com.onerpc.core.serialize.ProtocolEnum;
import com.onerpc.facade.model.RpcRequest;
import com.onerpc.facade.model.RpcResponse;
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
 * @version $Id: RpcServer.java, v 0.1 2019年04月30日 5:25 PM  Exp $
 */
public class RpcServer {

    public void startServer(int port) throws Exception {
        // 配置服务端的NIO线程组，网络事件处理
        // 网络事件处理
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // 启动NIO服务端的辅助启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //将创建的channel作为ServerSocketChannel
            serverBootstrap.group(boosGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // add的顺序决定了，handler处理的顺序
                            // 顺序不要弄混了
                            ch.pipeline().addLast(new MessageDecoder(
                                    RpcRequest.class, ProtocolEnum.FST));
                            ch.pipeline().addLast(new MessageEncoder(
                                    RpcResponse.class, ProtocolEnum.FST));
                            ch.pipeline().addLast(new RpcReceiveHandler());
                        }
                    });

            // 绑定端口，同步等待成功
            ChannelFuture cf = serverBootstrap.bind(port).sync();

            cf.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}