package com.xiaoqi.rpc.core;

import com.xiaoqi.rpc.model.RpcRequest;
import com.xiaoqi.rpc.model.RpcResponse;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 回调，当请求发出去之后，接收到返回数据时，回调方法
 */
public class RpcCallback {
    private RpcRequest  request;
    private RpcResponse response;
    private Lock        lock      = new ReentrantLock();
    private Condition   condition = lock.newCondition();

    private boolean flag = true;

    public RpcCallback(RpcRequest request) {
        this.request = request;
    }

    /**
     * 等待response
     *
     * @return
     */
    public Object waitResponse() {
        lock.lock();
        try {
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 获取返回值信息
            if (response != null) {
                return response.getResult();
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * 当收到查询结果之后，释放锁
     *
     * @param response
     */
    public void getResponse(RpcResponse response) {
        try {
            lock.lock();
            condition.signal();
            this.response = response;
            flag = false;
        } finally {
            lock.unlock();
        }
    }

}