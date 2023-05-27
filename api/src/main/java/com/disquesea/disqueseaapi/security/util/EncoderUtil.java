package com.disquesea.disqueseaapi.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EncoderUtil {

    public static String encodePassword(String password) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        return bCrypt.encode(password);
    }

    public static boolean passwordMatches(String password, String encodedPassword) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        return bCrypt.matches(password, encodedPassword);
    }

}
