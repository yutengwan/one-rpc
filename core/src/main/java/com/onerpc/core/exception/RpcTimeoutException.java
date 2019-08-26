package com.onerpc.core.exception;

/**
 * rpc 调用超时
 *
 * @version $Id: RpcTimeoutException.java
 */
public class RpcTimeoutException extends RuntimeException {
    public RpcTimeoutException(String message) {
        super(message);
    }
}