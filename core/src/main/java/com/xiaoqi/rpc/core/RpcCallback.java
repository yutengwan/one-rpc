package com.xiaoqi.rpc.core;

import com.xiaoqi.rpc.model.MessageRequest;
import com.xiaoqi.rpc.model.MessageResponse;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * �ص��������󷢳�ȥ֮�󣬽��յ���������ʱ���ص�����
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
     * �ȴ�response
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

            // ��ȡ����ֵ��Ϣ
            if (response != null) {
                return response.getResult();
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    /**
     * ���յ���ѯ���֮���ͷ���
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