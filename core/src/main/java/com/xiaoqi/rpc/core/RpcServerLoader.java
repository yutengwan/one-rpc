
package com.xiaoqi.rpc.core;

import com.xiaoqi.rpc.handler.RpcSendHandler;

/**
 * server端启动时，就需要初始化的数据
 *
 * @version $Id: RpcServerLoader.java
 */
public class RpcServerLoader {

    /**
     * 单例对象
     */
    private static volatile RpcServerLoader nettyServerLoader;

    /**
     * 消息发送handler
     */
    private RpcSendHandler nettyServerHandler;

    private RpcServerLoader() {}

    public static RpcServerLoader getInstance() {
        if (nettyServerLoader == null) {
            synchronized (RpcServerLoader.class) {
                if (nettyServerLoader == null) {
                    nettyServerLoader = new RpcServerLoader();
                }
            }
        }

        return nettyServerLoader;
    }

    public RpcSendHandler getNettyServerHandler() {
        return nettyServerHandler;
    }

    public void setNettyServerHandler(RpcSendHandler nettyServerHandler) {
        this.nettyServerHandler = nettyServerHandler;
    }
}