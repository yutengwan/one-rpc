package com.onerpc.core.serialize;

import com.onerpc.core.exception.RpcProtocolException;
import com.onerpc.core.handler.RpcSendHandler;
import com.onerpc.core.serialize.fst.FstSerializer;
import com.onerpc.core.serialize.json.JsonSerializer;
import com.onerpc.core.serialize.kryo.KryoSerializer;
import com.onerpc.core.util.LoggerHelper;
import com.onerpc.facade.model.RpcRequest;
import com.onerpc.facade.model.RpcResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

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
    private final Map<String, Serializer> serializerMap = new HashMap<>();

    public MessageEncoder(Class<T> tClass) {
        super(tClass);
        serializeClass = tClass;
        serializerMap.put(ProtocolEnum.FST.getCode(), new FstSerializer(serializeClass));
        serializerMap.put(ProtocolEnum.KRYO.getCode(), new KryoSerializer(serializeClass));
        serializerMap.put(ProtocolEnum.JSON.getCode(), new JsonSerializer(serializeClass));
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, T msg, ByteBuf out) throws Exception {
        String protocol = getProtocol(msg);

        if (StringUtils.isEmpty(protocol) || !serializerMap.containsKey(protocol)) {
            LoggerHelper.error(logger, "protocol={} is not exist", protocol);
            throw new RpcProtocolException("protocol " + protocol + " is not exits");
        }

        // 序列号协议数据信息
        int protocolLength = protocol.length();
        out.writeInt(protocolLength);
        out.writeBytes(protocol.getBytes());

        Serializer serializer = serializerMap.get(protocol);
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

    private String getProtocol(T msg) {
        String protocol = null;
        if (RpcRequest.class.isInstance(msg)) {
            RpcRequest request = (RpcRequest) msg;
            protocol = request.getProtocol();
        }

        if (RpcResponse.class.isInstance(msg)) {
            RpcResponse response = (RpcResponse) msg;
            protocol = response.getProtocol();
        }

        return protocol;
    }
}