package com.grocery.user.controller;

import com.grocery.common.redis.RedisUtil;
import com.grocery.user.mapper.UserCMapper;
import com.grocery.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * @Description 用户测试
 * @Author kongfh
 */
@RestController
@RequestMapping("test")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private UserCMapper userCMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/testRedis")
    @ApiOperation(value="测试接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "key", value = "redisKey", required = true, dataType = "String"),
            @ApiImplicitParam(name = "value", value = "redisValue", required = true, dataType = "String")})
   public String  testRedis(@RequestParam(value="key",required=true) String key ,@RequestParam(value="value",required=true)  String value){
       Long start =System.currentTimeMillis();
        //redisUtil.set(key,value);
        //System.err.println(userCMapper.selectList(null));
       // System.err.println(redisUtil.get(key));
      //  userService.getSysUser();
        System.err.println((System.currentTimeMillis()-start)/1000);
        return  "testSentinel";
   }
}
