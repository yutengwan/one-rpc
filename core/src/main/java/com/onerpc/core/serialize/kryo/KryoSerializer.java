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

    private final Class<T> genericClass;
    private final KryoPoolFactory kryoPoolFactory;

    public KryoSerializer(Class<T> genericClass) {
        this.genericClass = genericClass;
        kryoPoolFactory = new KryoPoolFactory(this.genericClass);
    }

    @Override
    public byte[] serialize(T t) throws Exception {
        Kryo kryo = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            kryo = kryoPoolFactory.getKryo();
            Output out = new Output(outputStream);
            kryo.writeClassAndObject(out, t);
            out.close();
            outputStream.close();
            return outputStream.toByteArray();
        } finally {
            if (kryo != null) {
                kryoPoolFactory.returnKryo(kryo);
            }
        }
    }

    @Override
    public T deserialize(byte[] body) throws Exception {
        Kryo kryo = null;
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(body);
            kryo = kryoPoolFactory.getKryo();
            Input in = new Input(input);
            T result = (T) kryo.readClassAndObject(in);
            in.close();
            input.close();
            return result;
        } finally {
            if (kryo != null) {
                kryoPoolFactory.returnKryo(kryo);
            }
        }
    }
}