package com.onerpc.core.spring;

import com.google.common.reflect.Reflection;
import com.onerpc.core.core.RpcSendProxy;
import com.onerpc.core.util.LoggerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * reference bean
 * 使用反射机制，远程调用服务
 *
 * @version $Id: OneRpcReference.java
 */
public class OneRpcReference implements FactoryBean, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(OneRpcReference.class);

    /**
     * interface
     */
    private String interfaceName;

    /**
     * rpc service name，unique 服务发布的标识
     */
    private String serviceName;

    /**
     * 序列号协议
     */
    private String protocol;

    /**
     * 请求超时
     */
    private String timeout;

    @Override
    public Object getObject() throws Exception {
        // 代理调用，调用远程发布的服务
        return Reflection.newProxy(getObjectType(), new RpcSendProxy(getObjectType(), serviceName, timeout, protocol));
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

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}