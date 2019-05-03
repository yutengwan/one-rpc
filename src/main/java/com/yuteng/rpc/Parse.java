/**
 * Alipay.com Inc. Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.yuteng.rpc;

/**
 *
 * @author wanyuteng
 * @version $Id: Parse.java, v 0.1 2019Äê04ÔÂ22ÈÕ 4:26 PM wanyuteng Exp $
 */
public class Parse {
    private String name;
    private String value;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Parse{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", age=" + age +
                '}';
    }
}