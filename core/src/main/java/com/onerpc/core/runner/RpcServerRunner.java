package com.onerpc.core.runner;

import com.onerpc.core.server.RpcServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 服务启动runner
 * @version $Id: RpcServerRunner.java, v 0.1 2019年05月21日 9:21 PM  Exp $
 */
@Component
public class RpcServerRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("rpc register runner");
        new Thread(() -> {
            try {
                new RpcServer().startServer(8877);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}