/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.onerpc.core.serialize;

/**
 *
 * Protocol enum
 * @version $Id: ProtocolEnum.java
 */
public enum ProtocolEnum {
    KRYO("KRYO", "kryo serialize"),
    FST("FST", "fast-serialization"),
    JSON("JSON", "json serialize");

    private String code;
    private String value;

    ProtocolEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}