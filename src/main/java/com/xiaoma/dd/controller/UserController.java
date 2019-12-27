package com.xiaoma.dd.controller;

import com.xiaoma.dd.dto.UserInfoParam;
import com.xiaoma.dd.dto.UserLoginParam;
import com.xiaoma.dd.component.CommonResult;
import com.xiaoma.dd.dto.UserRegisterParam;
import com.xiaoma.dd.pojo.User;
import com.xiaoma.dd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult register(@RequestBody UserRegisterParam userRegisterParam) {
        if (!userService.checkPhoneCode(userRegisterParam.getPhone(), userRegisterParam.getPhoneCode())) {
            return CommonResult.validateFailed("验证码错误");
        }
        User user = userService.register(userRegisterParam.getPhone(), userRegisterParam.getPassword(), userRegisterParam.getName());
        if (user == null) {
            return CommonResult.failed("注册失败");
        }
        return CommonResult.success("注册成功");
    }

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
    public CommonResult getUserInfo(Principal principal) {
        String phone = principal.getName();
        User user = userService.getUserByPhone(phone);
        List<String> data = new ArrayList<>();
        data.add(user.getPhone());
        data.add(user.getName());
        data.add(new SimpleDateFormat("yyyy-MM-dd").format(user.getCreateTime()));
        return CommonResult.success(data);
    }

    @RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateUserInfo(@RequestBody UserInfoParam userInfoParam, Principal principal) {
        String phone = principal.getName();
        if (userService.updateUserInfo(phone, userInfoParam)) {
            return CommonResult.success("修改成功");
        }
        return CommonResult.failed("修改失败");
    }
}
