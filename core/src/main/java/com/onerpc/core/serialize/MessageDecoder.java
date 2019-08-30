package com.onerpc.core.serialize;

import com.onerpc.core.handler.RpcSendHandler;
import com.onerpc.core.serialize.fst.FstSerializer;
import com.onerpc.core.serialize.json.JsonSerializer;
import com.onerpc.core.serialize.kryo.KryoSerializer;
import com.onerpc.core.util.LoggerHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version $Id: MessageDecoder.java
 */
public class MessageDecoder<T> extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(RpcSendHandler.class);

    private final Class<T>                serializeClass;
    private final Serializer              serializer;
    private final Map<String, Serializer> serializerMap = new HashMap<>();

    public MessageDecoder(Class<T> tClass, ProtocolEnum protocolEnum) {
        serializeClass = tClass;
        serializerMap.put(ProtocolEnum.FST.getCode(), new FstSerializer(serializeClass));
        serializerMap.put(ProtocolEnum.KRYO.getCode(), new KryoSerializer(serializeClass));
        serializerMap.put(ProtocolEnum.JSON.getCode(), new JsonSerializer(serializeClass));

        if (serializerMap.containsKey(protocolEnum.getCode())) {
            serializer = serializerMap.get(protocolEnum.getCode());
        } else {
            serializer = new JsonSerializer(serializeClass);
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