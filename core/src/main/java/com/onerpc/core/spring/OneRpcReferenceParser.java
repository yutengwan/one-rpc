package com.onerpc.core.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author wanyuteng
 * @version $Id: OneRpcReferenceParser.java
 */
public class OneRpcReferenceParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public OneRpcReferenceParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String id = element.getAttribute("id");
        String interfaceName = element.getAttribute("interface");
        String serviceName = element.getAttribute("serviceName");
        String protocolType = element.getAttribute("protocol");

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        beanDefinition.getPropertyValues().addPropertyValue("interfaceName", interfaceName);
        beanDefinition.getPropertyValues().addPropertyValue("serviceName", serviceName);
        beanDefinition.getPropertyValues().addPropertyValue("protocol", protocolType);

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        return beanDefinition;
    }
}