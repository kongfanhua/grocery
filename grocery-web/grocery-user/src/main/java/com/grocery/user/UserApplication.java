package com.grocery.user;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Description 用户启动器
 * @Author kongfh
 */
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.grocery.common","com.grocery.user"})
@EnableDiscoveryClient
@MapperScan("com.grocery.user.mapper")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
    @Bean
    @LoadBalanced
    public RestTemplate proviceRestTemplate() {
        return new RestTemplate();
    }
    @Bean
    public IRule myRule(){
        return new RetryRule();  //在这里选择负载均衡算法
    }
}
