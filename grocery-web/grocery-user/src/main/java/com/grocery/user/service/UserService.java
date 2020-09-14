package com.grocery.user.service;

import com.grocery.user.service.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description 用户测试
 * @Author kongfh
 */
@FeignClient(value = "grocery-sys", fallback = UserServiceImpl.class)
public interface UserService {
    /**
     * 获取系统用户
     */
    @RequestMapping(value = "sys/test/testFeign", method = RequestMethod.GET)
    void getSysUser();
}
