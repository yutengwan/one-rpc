
package com.onerpc.core.annotion;

import java.lang.annotation.*;

/**
 * rpc service注解的服务是对外提供的服务
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
    String serviceName() default "serviceName";
}