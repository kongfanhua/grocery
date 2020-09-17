package com.grocery.auth;

/**
 * @Description 路由
 * @Author kongfh
 */

import org.springframework.boot.SpringApplication;

@SpringCloudApplication
public class AuthApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(GatewayApplication.class, args);
    }


}
