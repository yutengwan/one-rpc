package com.onerpc.core.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author wanyuteng
 * @version $Id: OneRpcNamespaceHandler.java
 */
public class OneRpcNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("service", new OneRpcServiceParser(OneRpcService.class));
        registerBeanDefinitionParser("reference", new OneRpcReferenceParser(OneRpcReference.class));
    }
}