package com.onerpc.core.services;

import com.onerpc.core.annotion.RpcService;
import com.onerpc.facade.api.HelloService;

/**
 * @version $Id: HelloServiceImpl.java, v 0.1 2019年05月20日 7:36 PM Exp $
 */
@RpcService(serviceName = "helloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Server say hello:" + name;
    }
}