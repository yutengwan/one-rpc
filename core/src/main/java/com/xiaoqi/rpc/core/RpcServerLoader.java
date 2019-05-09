
package com.xiaoqi.rpc.core;

import com.xiaoqi.rpc.handler.RpcSendHandler;

/**
 * server������ʱ������Ҫ��ʼ��������
 *
 * @version $Id: RpcServerLoader.java, v 0.1 2019��05��07�� 5:27 PM  Exp $
 */
public class RpcServerLoader {

    /**
     * ��������
     */
    private static volatile RpcServerLoader nettyServerLoader;

    /**
     * ��Ϣ����handler
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