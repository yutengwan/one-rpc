package com.onerpc.core.client;

import com.google.common.reflect.Reflection;
import com.onerpc.api.HelloService;
import com.onerpc.core.core.RpcSendProxy;
import com.onerpc.core.core.RpcServerLoader;
import com.onerpc.core.handler.RpcSendHandler;
import com.onerpc.core.model.RpcRequest;
import com.onerpc.core.model.RpcResponse;
import com.onerpc.core.serialize.MessageDecoder;
import com.onerpc.core.serialize.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author xiaoqi
 * @version $Id: RpcClient.java
 */
public class RpcClient {

    private NioEventLoopGroup worker;

    public void start(String address, int port) throws InterruptedException {
        // 配置服务端的NIO线程组，网络事件处理
        // 网络事件处理
        worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        RpcSendHandler sendHandler = new RpcSendHandler();

        bootstrap.group(worker).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MessageDecoder(RpcResponse.class));
                        ch.pipeline().addLast(new MessageEncoder(RpcRequest.class));
                        ch.pipeline().addLast(sendHandler);
                    }
                }).option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.connect(address, port).sync();
        RpcServerLoader.getInstance().setNettyServerHandler(sendHandler);
    }

    public void stop() {
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        RpcClient rpcClient = new RpcClient();
        rpcClient.start("127.0.0.1", 8877);
        HelloService service = Reflection.newProxy(HelloService.class, new RpcSendProxy(HelloService.class));
        String result = service.sayHello("hello world");
        System.out.println(result);
    }

}