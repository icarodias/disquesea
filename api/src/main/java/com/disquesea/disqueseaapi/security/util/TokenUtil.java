package com.disquesea.disqueseaapi.security.util;

import com.disquesea.disqueseaapi.configuration.AppProperties;
import com.disquesea.disqueseaapi.domain.model.User;
import com.disquesea.disqueseaapi.security.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Component
public class TokenUtil {
    private final AppProperties properties;

    private static String TOKEN_KEY;

    private static String ISSUER;

    private static String TOKEN_HEADER;

    private static Date EXPIRATION;


    public TokenUtil(AppProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init(){
        TOKEN_KEY = properties.getJwt().getPrivateKey();
        ISSUER = properties.getJwt().getIssuer();
        TOKEN_HEADER = properties.getJwt().getHeaderPrefix();
        EXPIRATION = properties.getJwt().getExpiration();
    }

    public static AuthToken encodeToken(User user) {
        Key secretKey = Keys.hmacShaKeyFor(TOKEN_KEY.getBytes());
        String tokenJWT = Jwts.builder()
                                .setSubject(user.getUsername())
                                .setIssuer(ISSUER)
                                .setExpiration(EXPIRATION)
                                .addClaims(roleClaim(user))
                                .signWith(secretKey, SignatureAlgorithm.HS256)
                                .compact();

        return new AuthToken(TOKEN_HEADER + tokenJWT);
    }

    private static  Map<String, Object> roleClaim(User user) {
        final String ROLE_PREFIX = "ROLE_";

        return Map.of("role", ROLE_PREFIX + user.getRole().toString());
    }

    public static Authentication decodeToken(HttpServletRequest request) {
        try{
            String jwtToken = request.getHeader("Authorization");
            jwtToken = jwtToken.replace("Bearer ", "");

            // Decoding
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(TOKEN_KEY.getBytes())
                    .build()
                    .parseClaimsJws(jwtToken);

            // Extracting
            String username = jwsClaims.getBody().getSubject();
            String roleName = jwsClaims.getBody().get("role", String.class);

            GrantedAuthority authority = new SimpleGrantedAuthority(roleName);

            String issuer = jwsClaims.getBody().getIssuer();
            Date expiration = jwsClaims.getBody().getExpiration();

            if (!isEmpty(username) && issuer.equals(ISSUER) && expiration.after(new Date(System.currentTimeMillis()))) {
                return new UsernamePasswordAuthenticationToken("user", null, Arrays.asList(authority));
            }

        } catch (Exception e){
            System.out.println("DEBUG - ERRO AO DECODIFICAR TOKEN");
            System.out.println(e.getMessage());
        }

        return null;
    }
}