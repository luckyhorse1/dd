package com.xiaoma.dd.service;

import com.xiaoma.dd.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    String login(String phone, String password);

    User getUserByPhone(String phone);

    UserDetails loadUserByUsername(String phone);
}