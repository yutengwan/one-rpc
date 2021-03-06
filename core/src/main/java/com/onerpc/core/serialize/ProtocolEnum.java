
package com.onerpc.core.serialize;

/**
 *
 * Protocol enum
 * @version $Id: ProtocolEnum.java
 */
public enum ProtocolEnum {
    KRYO("kryo", "kryo serialize"),
    FST("fst", "fast-serialization"),
    JSON("json", "json serialize");

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