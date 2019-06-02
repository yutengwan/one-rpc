package com.xiaoqi.rpc.serialize;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xiaoqi
 * @version $Id: MessageEncoder.java
 */
public class MessageEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public MessageEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (genericClass.isInstance(msg)) {
            byte[] body = JSON.toJSONBytes(msg);
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
        }
    }
}