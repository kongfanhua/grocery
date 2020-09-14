package com.grocery.sys.feign;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 用户
 * @Author kongfh
 */
@Slf4j
@RefreshScope
@RestController
@RequestMapping(value = "/test")
@Api(tags = "课程预约", value = "课程预约")
public class UserFeign {

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
}
