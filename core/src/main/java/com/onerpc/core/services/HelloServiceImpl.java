package com.onerpc.core.services;

import com.onerpc.api.HelloService;
import com.onerpc.core.annotion.RpcService;
import org.springframework.stereotype.Service;

/**
 * @version $Id: HelloServiceImpl.java, v 0.1 2019年05月20日 7:36 PM Exp $
 */
@Service
@RpcService(serviceName = "HelloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Server say hello:" + name;
    }
}