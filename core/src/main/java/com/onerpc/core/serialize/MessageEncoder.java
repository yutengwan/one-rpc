package com.onerpc.core.serialize;

import com.onerpc.core.handler.RpcSendHandler;
import com.onerpc.core.serialize.json.JsonSerializer;
import com.onerpc.core.serialize.kryo.KryoSerializer;
import com.onerpc.core.util.LoggerHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

/**
 * @author xiaoqi
 * @version $Id: MessageEncoder.java
 */
public class MessageEncoder<T> extends MessageToByteEncoder<T> {

    private static final Logger logger = LoggerFactory.getLogger(RpcSendHandler.class);

    private final Class<T> genericClass;
    private Serializer serializer;

    public MessageEncoder(Class<T> tClass, ProtocolEnum protocolEnum) {
        super(tClass);
        genericClass = tClass;
        switch (protocolEnum) {
            case KRYO:
                serializer = new KryoSerializer(genericClass);
                break;
            case JSON:
                serializer = new JsonSerializer(genericClass);
                break;
            default:
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, T msg, ByteBuf out) throws Exception {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] body = serializer.serialize(msg);
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
            byteArrayOutputStream.close();
        } catch (Exception e) {
            LoggerHelper.error(logger, "encode exception", e);
            throw e;
        }
    }
}