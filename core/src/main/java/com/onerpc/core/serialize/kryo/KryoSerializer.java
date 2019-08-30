package com.onerpc.core.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.onerpc.core.serialize.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @version $Id: KryoSerializer.java
 */
public class KryoSerializer<T> implements Serializer<T> {

    private final Class<T>        serializeClass;
    private final KryoPoolFactory kryoPoolFactory;

    public KryoSerializer(Class<T> serializeClass) {
        this.serializeClass = serializeClass;
        kryoPoolFactory = new KryoPoolFactory(this.serializeClass);
    }

    @Override
    public byte[] serialize(T t) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Kryo kryo = kryoPoolFactory.getKryo();
        Output out = new Output(outputStream);
        try {
            kryo.writeClassAndObject(out, t);
            out.flush();
            return outputStream.toByteArray();
        } finally {
            out.close();
            outputStream.close();
            if (kryo != null) {
                kryoPoolFactory.returnKryo(kryo);
            }
        }
    }

    @Override
    public T deserialize(byte[] body) throws Exception {
        ByteArrayInputStream input = new ByteArrayInputStream(body);
        Kryo kryo = kryoPoolFactory.getKryo();
        Input in = new Input(input);
        try {
            T result = (T) kryo.readClassAndObject(in);
            return result;
        } finally {
            if (kryo != null) {
                kryoPoolFactory.returnKryo(kryo);
            }
            input.close();
            in.close();
        }
    }
}