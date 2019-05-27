package com.xiaoqi.rpc.runner;

import com.xiaoqi.rpc.annotion.RpcService;
import com.xiaoqi.rpc.core.RegisterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 服务注册runner，获取rpc实现服务
 * @version $Id: ServiceRegisterRunner.java, v 0.1 2019年05月21日 9:34 PM  Exp $
 */
@Component
public class ServiceRegisterRunner implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("service register");
        Map<String, Object> beans = appContext.getBeansWithAnnotation(RpcService.class);

        // 获取服务service列表
        beans.forEach((key, value) -> {
            RpcService rpcService = appContext.findAnnotationOnBean(key, RpcService.class);
            RegisterServices.getInstance().registerService(rpcService.serviceName(), value);
        });
    }
}