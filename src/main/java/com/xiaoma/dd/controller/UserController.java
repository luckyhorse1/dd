package com.xiaoma.dd.controller;

import com.xiaoma.dd.dto.UserLoginParam;
import com.xiaoma.dd.api.CommonResult;
import com.xiaoma.dd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginParam userLoginParam) {
        if (userService.login(userLoginParam.getUsername(), userLoginParam.getPassword())) {
            return CommonResult.success("登录成功");
        }
        return CommonResult.failed("登录失败");
    }
}
