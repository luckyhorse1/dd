package com.xiaoma.dd.controller;

import com.xiaoma.dd.dto.UserLoginParam;
import com.xiaoma.dd.mapper.UserMapper;
import com.xiaoma.dd.pojo.User;
import com.xiaoma.dd.pojo.UserExample;
import com.xiaoma.dd.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@RequestBody UserLoginParam userLoginParam) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(userLoginParam.getUsername()).andPasswordEqualTo(userLoginParam.getPassword());
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            return CommonResult.failed("登录失败");
        }
        return CommonResult.success("登录成功");
    }
}
