/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.onerpc.core.serialize;

/**
 * 序列化，反序列化
 *
 * @version $Id: Serializer.java
 */
public interface Serializer<T> {

    /**
     * serialize t in outputstream
     * @param t
     * @return
     */
    public byte[] serialize(T t) throws Exception;

    /**
     * deserialize inputstream to t
     * @param body
     * @return
     */
    public T deserialize(byte[] body) throws Exception;
}