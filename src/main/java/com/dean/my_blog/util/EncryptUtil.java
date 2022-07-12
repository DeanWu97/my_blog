package com.dean.my_blog.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptUtil {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static boolean match(String passWord, String encrptPassWord) {
        return passwordEncoder.matches(passWord, encrptPassWord);
    }

    public static String encrypt(String password) {
        return passwordEncoder.encode(password);
    }
}
