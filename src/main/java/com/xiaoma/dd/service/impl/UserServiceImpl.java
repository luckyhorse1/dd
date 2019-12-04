package com.xiaoma.dd.service.impl;

import com.xiaoma.dd.component.MyUserDetails;
import com.xiaoma.dd.mapper.UserMapper;
import com.xiaoma.dd.pojo.User;
import com.xiaoma.dd.pojo.UserExample;
import com.xiaoma.dd.service.UserService;
import com.xiaoma.dd.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(String phone, String password) {
        String token = null;
        try {
            UserDetails userDetails = loadUserByUsername(phone);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            System.out.println("登录异常");
        }
        return token;
    }

    @Override
    public User getUserByPhone(String phone) {
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        List<User> userList = userMapper.selectByExample(example);
        if (userList != null && userList.size()>0)
            return userList.get(0);
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) {
        User user = getUserByPhone(phone);
        if (user != null) {
            return new MyUserDetails(user);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
