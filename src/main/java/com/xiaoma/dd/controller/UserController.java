package com.xiaoma.dd.controller;

import com.xiaoma.dd.mapper.UserMapper;
import com.xiaoma.dd.pojo.User;
import com.xiaoma.dd.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseBody
    public Object login() {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo("17633907915");
        List<User> users = userMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(users)) {
            User user = users.get(0);
            System.out.println("haha");
            return user.getName();
        }
        return "hello world";
    }
}
