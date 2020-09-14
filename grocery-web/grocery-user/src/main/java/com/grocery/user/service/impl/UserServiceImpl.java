package com.grocery.user.service.impl;

import com.grocery.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Description 熔断
 * @Author kongfh
 */
@Service
public class UserServiceImpl  implements UserService {
    /**
     * 获取系统用户
     */
    @Override
    public void getSysUser() {
        System.err.println("天堂关门了>_<,进地狱吧");
    }
}
