package com.onerpc.core.serialize;

import com.onerpc.core.handler.RpcSendHandler;
import com.onerpc.core.serialize.json.JsonSerializer;
import com.onerpc.core.serialize.kryo.KryoSerializer;
import com.onerpc.core.util.LoggerHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @version $Id: MessageDecoder.java
 */
public class MessageDecoder<T> extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(RpcSendHandler.class);

    private final Class<T> genericClass;
    private Serializer serializer;

    public MessageDecoder(Class<T> tClass, ProtocolEnum protocolEnum) {
        genericClass = tClass;
        switch (protocolEnum) {
            case KRYO:
                serializer = new KryoSerializer(genericClass);
                break;
            case JSON:
                serializer = new JsonSerializer(genericClass);
            default:
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }

        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        try {
            byte[] body = new byte[dataLength];
            in.readBytes(body);
            Object o = serializer.deserialize(body);
            out.add(o);
        } catch (Exception e) {
            LoggerHelper.error(logger, "decode exception", e);
            throw e;
        }

    }
}