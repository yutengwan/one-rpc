package com.onerpc.core.serialize.fst;

import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * fast-serialization
 *
 * @version $Id: FstFactory.java
 */
public class FstFactory {

    private final FSTConfiguration fstConfiguration;
    private final Class<?>         serializeClass;

    public FstFactory(Class<?> serializeClass) {
        this.serializeClass = serializeClass;
        fstConfiguration = FSTConfiguration.createDefaultConfiguration();
        fstConfiguration.registerClass(this.serializeClass);
    }

    /**
     * get out stream
     *
     * @param outputStream
     * @return
     */
    public FSTObjectOutput getObjectOutput(OutputStream outputStream) {
        return fstConfiguration.getObjectOutput(outputStream);
    }

    /**
     * get in stream
     *
     * @param inputStream
     * @return
     */
    public FSTObjectInput getObjectInput(InputStream inputStream) {
        return fstConfiguration.getObjectInput(inputStream);
    }
}