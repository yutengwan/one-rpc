
package com.xiaoqi.rpc.model;

import java.io.Serializable;

/**
 * 消息体描述
 *
 * @author
 * @version $Id: MessageRequest.java, v 0.1 2019年05月03日 10:04 PM  Exp $
 */
public class MessageRequest implements Serializable {
    /**
     * 消息唯一Id，request和response根据id映射
     */
    private String messageId;

    /**
     * 调用类的服务
     */
    private String className;

    /**
     * 类对应的方法
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] typeParameters;

    /**
     * 参数值
     */
    private Object[] parametersVal;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(Class<?>[] typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Object[] getParametersVal() {
        return parametersVal;
    }

    public void setParametersVal(Object[] parametersVal) {
        this.parametersVal = parametersVal;
    }
}