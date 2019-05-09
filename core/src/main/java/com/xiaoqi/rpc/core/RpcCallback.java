package com.xiaoqi.rpc.core;

import com.xiaoqi.rpc.model.MessageRequest;
import com.xiaoqi.rpc.model.MessageResponse;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 回调，当请求发出去之后，接收到返回数据时，回调方法
 */
public class RpcCallback {
    private MessageRequest  request;
    private MessageResponse response;
    private Lock            lock      = new ReentrantLock();
    private Condition       condition = lock.newCondition();

    public RpcCallback(MessageRequest request) {
        this.request = request;
    }

    /**
     * 等待response
     *
     * @return
     */
    public Object waitResponse() {
        try {
            lock.lock();
            try {
                condition.wait();
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
    public void getResponse(MessageResponse response) {
        try {
            lock.lock();
            condition.signal();
            this.response = response;
        } finally {
            lock.unlock();
        }
    }

}