package com.onerpc.core.serialize.json;

import com.alibaba.fastjson.JSON;
import com.onerpc.core.serialize.Serializer;

import java.io.IOException;

/**
 * @author
 * @version $Id: JsonSerializer.java
 */
public class JsonSerializer<T> implements Serializer<T> {
    private final Class<T> serializeClass;

    public JsonSerializer(Class<T> serializeClass) {
        this.serializeClass = serializeClass;
    }

    @Override
    public byte[] serialize(T t) throws IOException {
        return JSON.toJSONBytes(t);
    }

    @Override
    public T deserialize(byte[] input) throws IOException {
        return (T) JSON.parseObject(input, serializeClass);
    }
}