package com.xiaoma.dd.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Test {

    public static void main(String[] args) {
        System.out.println(isPass("xiaoma521", "$2a$10$i6o023Hy0BRadoeLp6y5FOgOQzbJ2v95PBZquSYBIMDNcbQDNa4WO"));
    }

    public static String encodePass(String pass) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pass);
    }

    public static boolean isPass(String pass, String encodePass) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(pass, encodePass);
    }
}
