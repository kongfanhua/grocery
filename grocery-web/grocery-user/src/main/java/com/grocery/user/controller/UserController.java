package com.grocery.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.grocery.common.enums.ResponseCodeEnum;
import com.grocery.common.model.ResponseResult;
import com.grocery.common.redis.RedisUtil;
import com.grocery.common.util.JWTUtil;
import com.grocery.user.mapper.UserCMapper;
import com.grocery.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


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

    @GetMapping("/testGateway")
    @ApiOperation(value="测试网关")
    public String  testGateway(){
        return  "testGateway";
    }



    //@Value("${secretKey:123456}")
    private String secretKey;



    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseResult login(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseResult.error(ResponseCodeEnum.PARAMETER_ILLEGAL.getCode(), ResponseCodeEnum.PARAMETER_ILLEGAL.getMessage());
        }

        String username = "world";
        String password = "hello";
        //  假设查询到用户ID是1001
        String userId = "1001";
        if ("hello".equals(username) && "world".equals(password)) {
            //  生成Token
            String token = JWTUtil.generateToken(userId, secretKey);

            //  生成刷新Token
            String refreshToken = UUID.randomUUID().toString().replace("-", "");

            /**
             * 如果可以允许用户退出后token如果在有效期内仍然可以使用的话，那么就不需要存Redis
             * 因为，token要跟用户做关联的话，就必须得每次都带一个用户标识，
             * 那么校验token实际上就变成了校验token和用户标识的关联关系是否正确，且token是否有效
             */
            Map<String,String> map = new HashMap<String,String>();
            String key = userId;
            map.put("token", token);
            map.put( "refreshToken", refreshToken);
            redisUtil.add(key,map);
            redisUtil.expire(key,JWTUtil.TOKEN_EXPIRE_TIME);

            JSONObject  result  = new  JSONObject();
            result.put("token",token);
            result.put("refreshToken",refreshToken);
            result.put("userId",userId);

            return ResponseResult.success(result);
        }

        return ResponseResult.error(ResponseCodeEnum.LOGIN_ERROR.getCode(), ResponseCodeEnum.LOGIN_ERROR.getMessage());
    }

    /**
     * 退出
     */
    @GetMapping("/logout")
    public ResponseResult logout(@RequestParam("userId") String userId) {
        redisUtil.delete(userId);
        return ResponseResult.success();
    }

    /**
     * 刷新Token
     */
    @PostMapping("/refreshToken")
    public ResponseResult refreshToken( BindingResult bindingResult) {
//        String userId = request.getUserId();
//        String refreshToken = request.getRefreshToken();
//        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
//        String key = userId;
//        String originalRefreshToken = hashOperations.get(key, "refreshToken");
//        if (StringUtils.isBlank(originalRefreshToken) || !originalRefreshToken.equals(refreshToken)) {
//            return ResponseResult.error(ResponseCodeEnum.REFRESH_TOKEN_INVALID.getCode(), ResponseCodeEnum.REFRESH_TOKEN_INVALID.getMessage());
//        }
//
//        //  生成新token
//        String newToken = JWTUtil.generateToken(userId, secretKey);
//        hashOperations.put(key, "token", newToken);
//        stringRedisTemplate.expire(userId, JWTUtil.TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        return ResponseResult.success("newToken");
    }


}
