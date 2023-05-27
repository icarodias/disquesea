package com.disquesea.disqueseaapi.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class EncoderUtil {

    public String encodePassword(String password) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        return bCrypt.encode(password);
    }

    public boolean passwordMatches(String password, String encodedPassword) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        return bCrypt.matches(password, encodedPassword);
    }

}
