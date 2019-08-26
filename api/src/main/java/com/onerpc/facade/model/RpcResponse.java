
package com.onerpc.facade.model;

import java.io.Serializable;

public class RpcResponse implements Serializable {
    /**
     * request id, 请求唯一
     */
    private String requestId;

    /**
     * 返回结果信息
     */
    private Object result;

    /**
     * 异常信息，rpc exception
     */
    private String error;

    /**
     * client请求超时时间
     */
    private int clientTimeout;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getClientTimeout() {
        return clientTimeout;
    }

    public void setClientTimeout(int clientTimeout) {
        this.clientTimeout = clientTimeout;
    }
}