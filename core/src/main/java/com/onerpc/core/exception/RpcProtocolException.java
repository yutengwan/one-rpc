package com.onerpc.core.exception;

/**
 * rpc protocol exception
 *
 * @version $Id: RpcProtocolException.java
 */
public class RpcProtocolException extends RuntimeException {
    public RpcProtocolException(String message) {
        super(message);
    }
}