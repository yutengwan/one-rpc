
package com.onerpc.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ImportResource({"classpath:one-rpc-service.xml"})
public class Application {
    public static void main(String [] args) {
        SpringApplication.run(Application.class);
    }
}