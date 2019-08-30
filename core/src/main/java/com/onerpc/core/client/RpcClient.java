package com.onerpc.core.client;

import com.onerpc.core.core.RpcServerLoader;
import com.onerpc.core.handler.RpcSendHandler;
import com.onerpc.core.serialize.MessageDecoder;
import com.onerpc.core.serialize.MessageEncoder;
import com.onerpc.core.serialize.ProtocolEnum;
import com.onerpc.core.util.LoggerHelper;
import com.onerpc.facade.api.HelloService;
import com.onerpc.facade.model.RpcRequest;
import com.onerpc.facade.model.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * rpc client for connection test
 *
 * @version $Id: RpcClient.java
 */
public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

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
                        ch.pipeline().addLast(new MessageDecoder(RpcResponse.class, ProtocolEnum.FST));
                        ch.pipeline().addLast(new MessageEncoder(RpcRequest.class, ProtocolEnum.FST));
                        ch.pipeline().addLast(sendHandler);
                    }
                }).option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.connect(address, port).sync();
        // 注册 client server handler
        // 远程调用的时候，使用到改handler
        RpcServerLoader.getInstance().setNettyServerHandler(sendHandler);
    }

    public void stop() {
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        RpcClient rpcClient = new RpcClient();
        rpcClient.start("127.0.0.1", 8877);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:one-rpc-client.xml");

        int threadNums = 100;
        CountDownLatch countDownLatch = new CountDownLatch(threadNums);
        HelloService service = (HelloService) context.getBean("rpcReference");
        for (int i = 0; i < threadNums; i++) {
            int finalI = i;
            new Thread(() -> {
                String result = service.sayHello("hello world:" + String.valueOf(finalI));
                LoggerHelper.info(logger, "receive result={}", result);
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        rpcClient.stop();
    }
}