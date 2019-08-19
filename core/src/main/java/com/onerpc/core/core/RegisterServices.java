package com.onerpc.core.core;

import com.onerpc.core.util.LoggerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务注册管理的service， 管理的是服务实现的列表
 *
 * @version $Id: RegisterServices.java, v 0.1 2019年05月20日 4:01 PM Exp $
 */
public class RegisterServices {

    private static final    Logger           logger = LoggerFactory.getLogger(RegisterServices.class);
    /**
     * 单例
     */
    private static volatile RegisterServices registerServices;

    /**
     * 服务的列表
     */
    private ConcurrentHashMap<String, Object> serviceMap = new ConcurrentHashMap<>();

    private RegisterServices() {}

    public static RegisterServices getInstance() {
        if (registerServices == null) {
            synchronized (RegisterServices.class) {
                if (registerServices == null) {
                    registerServices = new RegisterServices();
                }
            }
        }

        return registerServices;
    }

    /**
     * 注册服务
     *
     * @param name
     * @param bean
     */
    public void registerService(String name, Object bean) {
        LoggerHelper.info(logger, "register service, serviceName={}", name);
        serviceMap.put(name, bean);
    }

    /**
     * 注销服务
     *
     * @param name
     */
    public void unregisterService(String name) {
        serviceMap.remove(name);
    }

    /**
     * 获取服务
     *
     * @param name
     * @return
     */
    public Object getService(String name) {
        return serviceMap.get(name);
    }
}