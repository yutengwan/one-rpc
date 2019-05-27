package com.xiaoqi.rpc.exception;

/**
 * service not exit
 *
 * @author wanyuteng
 * @version $Id: ServiceNotExistException.java
 */
public class ServiceNotExistException extends RuntimeException {

    public ServiceNotExistException() {
        super();
    }

    public ServiceNotExistException(String message) {
        super(message);
    }
}