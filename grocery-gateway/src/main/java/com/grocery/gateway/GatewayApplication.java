package com.grocery.gateway;

/**
 * @Description 路由
 * @Author kongfh
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(GatewayApplication.class, args);
        log.info("Gateway启动成功");
    }

}
