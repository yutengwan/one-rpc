package com.onerpc.core.spring;

import com.onerpc.core.core.RegisterServices;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

/**
 * @author
 * @version $Id: OneRpcService.java
 */
public class OneRpcService implements ApplicationContextAware {
    private String serviceName;

    private String ref;

    private String interfaceName;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        Object bean = applicationContext.getBean(ref);

        // 注册rpc service
        if (StringUtils.isEmpty(serviceName)) {
            RegisterServices.getInstance().registerService(interfaceName, bean);
        } else {
            RegisterServices.getInstance().registerService(serviceName, bean);
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}