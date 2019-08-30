package com.onerpc.core.serialize;

import com.onerpc.core.handler.RpcSendHandler;
import com.onerpc.core.serialize.fst.FstSerializer;
import com.onerpc.core.serialize.json.JsonSerializer;
import com.onerpc.core.serialize.kryo.KryoSerializer;
import com.onerpc.core.util.LoggerHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *  object encode
 *
 * @version $Id: MessageEncoder.java
 */
public class MessageEncoder<T> extends MessageToByteEncoder<T> {

    private static final Logger logger = LoggerFactory.getLogger(RpcSendHandler.class);

    private final Class<T>                serializeClass;
    private final Serializer              serializer;
    private final Map<String, Serializer> serializerMap = new HashMap<>();

    public MessageEncoder(Class<T> tClass, ProtocolEnum protocolEnum) {
        super(tClass);
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
    protected void encode(ChannelHandlerContext ctx, T msg, ByteBuf out) throws Exception {
        try {
            byte[] body = serializer.serialize(msg);
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
        } catch (Exception e) {
            LoggerHelper.error(logger, "encode exception", e);
            throw e;
        }
    }
}