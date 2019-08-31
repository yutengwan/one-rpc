package com.onerpc.core.core;

import com.onerpc.core.exception.RpcInvokeException;
import com.onerpc.core.exception.RpcTimeoutException;
import com.onerpc.core.util.LoggerHelper;
import com.onerpc.facade.model.RpcRequest;
import com.onerpc.facade.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 回调，当请求发出去之后，接收到返回数据时，回调方法
 */
public class RpcCallback {
    private static final Logger logger = LoggerFactory.getLogger(RpcCallback.class);

    private RpcRequest  request;
    private RpcResponse response;
    private Lock        lock      = new ReentrantLock();
    private Condition   condition = lock.newCondition();

    /**
     * 请求超时时间, 默认5s
     */
    private long timeout = 5000;

    public RpcCallback(RpcRequest request) {
        this.request = request;
    }

    public RpcCallback(RpcRequest request, String timeout) {
        this.request = request;
        if (!StringUtils.isEmpty(timeout)) {
            this.timeout = Long.parseLong(timeout);
        }
    }

    /**
     * 等待response
     *
     * @return
     */
    public Object waitResponse() {
        lock.lock();
        boolean ret = false;
        try {
            try {
                ret = condition.await(timeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                LoggerHelper.error(logger, "await interruption exception", e);
            }

            if (!ret) {
                throw new RpcTimeoutException("request timeout");
            }

            // 获取返回值信息
            if (response != null) {
                if (!StringUtils.isEmpty(response.getError())) {
                    throw new RpcInvokeException(response.getError());
                }
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
        } finally {
            lock.unlock();
        }
    }
}