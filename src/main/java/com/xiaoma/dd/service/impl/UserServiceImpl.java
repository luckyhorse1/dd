package com.xiaoma.dd.service.impl;

import com.xiaoma.dd.mapper.UserMapper;
import com.xiaoma.dd.pojo.User;
import com.xiaoma.dd.pojo.UserExample;
import com.xiaoma.dd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean login(String username, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(username).andPasswordEqualTo(password);
        List<User> userList = userMapper.selectByExample(example);
        return !userList.isEmpty();
    }
}
