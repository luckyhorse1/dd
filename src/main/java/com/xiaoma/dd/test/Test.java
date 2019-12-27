package com.xiaoma.dd.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
        testDate();
    }

    public static void encodePass(String pass) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        encoder.encode(pass);
    }

    public static void isPass(String pass, String encodePass) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        encoder.matches(pass, encodePass);
    }

    public static void testDate(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String s = dateFormat.format(date);
        System.out.println(s);
    }
}
