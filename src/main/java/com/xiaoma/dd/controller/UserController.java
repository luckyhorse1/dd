package com.xiaoma.dd.controller;

import com.xiaoma.dd.dto.UserLoginParam;
import com.xiaoma.dd.component.CommonResult;
import com.xiaoma.dd.pojo.User;
import com.xiaoma.dd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginParam userLoginParam) {

        String token = userService.login(userLoginParam.getPhone(), userLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @RequestMapping(value = "/getPhoneCode", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getPhoneCode(@RequestParam String phone) {
        System.out.println(phone);
        User user = userService.getUserByPhone(phone);
        System.out.println(user);
        if (user != null) return CommonResult.failed("该用户已注册");
        return CommonResult.success(userService.generatePhoneCode(phone));
    }

    @RequestMapping(value = "/checkPhoneCode", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult checkPhoneCode(@RequestParam String phone, @RequestParam String code) {
        if (userService.checkPhoneCode(phone, code)) {
            return CommonResult.success("验证成功");
        }
        return CommonResult.validateFailed("验证失败");
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getInfo(Principal principal) {
        String phone = principal.getName();
        User user = userService.getUserByPhone(phone);
        Map<String, Object> data = new HashMap<>();
        data.put("name", user.getName());
        return CommonResult.success(data);
    }
}
