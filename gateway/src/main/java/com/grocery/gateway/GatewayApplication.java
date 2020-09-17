package com.grocery.gateway;

/**
 * @Description 路由
 * @Author kongfh
 */

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class GatewayApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(GatewayApplication.class, args);
    }


}
