package com.grocery.sys.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 用户
 * @Author kongfh
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping(value = "/test")
@Api(tags = "用户管理", value = "用户管理")
public class UserController {

    @GetMapping("/testFeign")
    @ApiOperation(value="测试远程调用")
    public void  testFeign(){
        try
        {
            Thread.sleep(5000);    //延时5秒
            System.err.println("欢迎来到天堂管理系统");
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @GetMapping("/testGateway")
    @ApiOperation(value="测试网关")
    public String  testGateway(){
        return  "testGateway";
    }
}
