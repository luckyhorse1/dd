package com.xiaoma.dd.service;

import com.xiaoma.dd.dto.UserInfoParam;
import com.xiaoma.dd.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    User register(String phone, String password, String name);

    String login(String phone, String password);

    User getUserByPhone(String phone);

    UserDetails loadUserByUsername(String phone);

    String generatePhoneCode(String phone);

    boolean checkPhoneCode(String phone, String code);

    boolean updateUserInfo(String phone, UserInfoParam param);

    boolean updatePhone(String oldPhone, String newPhone);

    boolean updatePass(String phone, String newPass);
}
