package com.onerpc.core.serialize.fst;

import com.onerpc.core.serialize.Serializer;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * fast-serialization
 *
 * @version $Id: FstSerializer.java
 */
public class FstSerializer<T> implements Serializer<T> {
    private final Class<T>   serializeClass;
    private final FstFactory fstFactory;

    public FstSerializer(Class<T> serializeClass) {
        this.serializeClass = serializeClass;
        fstFactory = new FstFactory(this.serializeClass);
    }

    @Override
    public byte[] serialize(T t) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FSTObjectOutput out = fstFactory.getObjectOutput(outputStream);
        try {
            out.writeObject(t);

            // flush before get bytes
            // or close, flush before close stream
            out.flush();
            return outputStream.toByteArray();
        } finally {
            outputStream.close();
            out.close();
        }

    }

    @Override
    public T deserialize(byte[] body) throws Exception {
        ByteArrayInputStream input = new ByteArrayInputStream(body);
        FSTObjectInput in = fstFactory.getObjectInput(input);
        try {
            T t = (T) in.readObject();
            return t;
        } finally {
            input.close();
            in.close();
        }
    }
}