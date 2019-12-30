package com.xiaoma.dd.service.impl;

import com.xiaoma.dd.component.MyUserDetails;
import com.xiaoma.dd.dto.UserInfoParam;
import com.xiaoma.dd.mapper.UserMapper;
import com.xiaoma.dd.pojo.User;
import com.xiaoma.dd.pojo.UserExample;
import com.xiaoma.dd.service.RedisService;
import com.xiaoma.dd.service.UserService;
import com.xiaoma.dd.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisService redisService;

    @Value("${redis.key.prefix.authCode}")
    private String REDIS_KEY_PREFIX_AUTH_CODE;

    @Value("${authCode.expire.seconds}")
    private Long AUTH_CODE_EXPIRE_SECONDS;


    @Override
    public User register(String phone, String password, String name) {
        User user = new User();
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setCreateTime(new Date());
        userMapper.insertSelective(user);
        return user;
    }

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
    public String generatePhoneCode(String phone) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            code.append(random.nextInt(10));
        }
        redisService.set(REDIS_KEY_PREFIX_AUTH_CODE+phone, code.toString());
        redisService.expire(REDIS_KEY_PREFIX_AUTH_CODE+phone, AUTH_CODE_EXPIRE_SECONDS);
        return code.toString();
    }

    @Override
    public boolean checkPhoneCode(String phone, String code) {
        String realCode = redisService.get(REDIS_KEY_PREFIX_AUTH_CODE+phone);
        if (realCode==null) return false;
        return realCode.equals(code);
    }

    @Override
    public boolean updateUserInfo(String phone, UserInfoParam param) {
        User user = getUserByPhone(phone);
        user.setName(param.getName());
        user.setSex(param.getSex());
        user.setAge(param.getAge());
        user.setWx(param.getWx());
        UserExample example = new UserExample();
        example.createCriteria().andPhoneEqualTo(phone);
        userMapper.updateByExample(user, example);
        return true;
    }

    @Override
    public boolean updatePhone(String oldPhone, String newPhone) {
        User user = getUserByPhone(oldPhone);
        user.setPhone(newPhone);
        userMapper.updateByPrimaryKey(user);
        return true;
    }

    @Override
    public boolean updatePass(String phone, String newPass) {
        User user = getUserByPhone(phone);
        user.setPassword(new BCryptPasswordEncoder().encode(newPass));
        userMapper.updateByPrimaryKey(user);
        return true;
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
