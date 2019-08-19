package com.onerpc.core.spring;

import com.google.common.reflect.Reflection;
import com.onerpc.core.core.RpcSendProxy;
import com.onerpc.core.util.LoggerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author wanyuteng
 * @version $Id: OneRpcReference.java
 */
public class OneRpcReference implements FactoryBean, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(OneRpcReference.class);

    private String interfaceName;
    private String serviceName;
    private String protocol;

    @Override
    public Object getObject() throws Exception {
        return Reflection.newProxy(getObjectType(), new RpcSendProxy(getObjectType(), serviceName));
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return getClass().getClassLoader().loadClass(interfaceName);
        } catch (ClassNotFoundException e) {
            LoggerHelper.warn(logger, "reference parse failed", e);
            return null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}