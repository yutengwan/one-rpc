
package com.xiaoqi.rpc.model;

import java.io.Serializable;

/**
 * ��Ϣ������
 *
 * @author
 * @version $Id: MessageRequest.java, v 0.1 2019��05��03�� 10:04 PM  Exp $
 */
public class MessageRequest implements Serializable {
    /**
     * ��ϢΨһId��request��response����idӳ��
     */
    private String messageId;

    /**
     * ������ķ���
     */
    private String className;

    /**
     * ���Ӧ�ķ���
     */
    private String methodName;

    /**
     * ��������
     */
    private Class<?>[] typeParameters;

    /**
     * ����ֵ
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