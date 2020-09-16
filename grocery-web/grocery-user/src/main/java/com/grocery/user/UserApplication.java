package com.grocery.user;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description 用户启动器
 * @Author kongfh
 */
@Slf4j
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.grocery.common","com.grocery.user"})
@EnableDiscoveryClient
@MapperScan("com.grocery.user.mapper")
public class UserApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(UserApplication.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 运行成功! 访问连接:\n\t" +
                        "Swagger文档: \t\thttp://{}:{}{}{}/doc.html\n\t" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path", ""),
                env.getProperty("spring.mvc.servlet.path", "")
        );
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
