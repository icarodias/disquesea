package com.disquesea.disqueseaapi.security.components;

import com.disquesea.disqueseaapi.configuration.AppProperties;
import com.disquesea.disqueseaapi.domain.model.User;
import com.disquesea.disqueseaapi.security.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;
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
public class TokenJwtUtil {
    private final AppProperties properties;
    private final String tokenKey;
    private final String tokenHeader;
    private final String issuer;
    private final Date expiration;

    public TokenJwtUtil(AppProperties properties) {
        this.properties = properties;
        this.tokenKey = properties.getJwt().getPrivateKey();
        this.tokenHeader = properties.getJwt().getHeaderPrefix();
        this.issuer = properties.getJwt().getIssuer();
        this.expiration = properties.getJwt().getExpiration();
    }

    public AuthToken encodeToken(User user) {
        Key secretKey = Keys.hmacShaKeyFor(tokenKey.getBytes());
        String tokenJWT = Jwts.builder()
                                .setSubject(user.getUsername())
                                .setIssuer(issuer)
                                .setExpiration(expiration)
                                .addClaims(roleClaim(user))
                                .signWith(secretKey, SignatureAlgorithm.HS256)
                                .compact();

        return new AuthToken(tokenHeader + tokenJWT);
    }

    private  Map<String, Object> roleClaim(User user) {
        final String ROLE_PREFIX = "ROLE_";

        return Map.of("role", ROLE_PREFIX + user.getRole().toString());
    }

    public Authentication decodeToken(HttpServletRequest request) {
        try{
            String jwtToken = request.getHeader("Authorization");

            jwtToken = formattingJwtToken(jwtToken);

            Jws<Claims> jwsClaims = generateClaimsFromJwtToken(jwtToken);

            JwtContent jwtContent = extract(jwsClaims);

            final boolean isValidToken = isValidToken(jwtContent);

            if (isValidToken) {
                final GrantedAuthority authority = jwtContent.getAuthority();
                return new UsernamePasswordAuthenticationToken("user", null, Arrays.asList(authority));
            }

        } catch (Exception e){
            System.out.println("DEBUG - ERRO AO DECODIFICAR TOKEN");
            System.out.println(e.getMessage());
        }

        return null;
    }

    private String formattingJwtToken(String jwt) {
        return jwt.replace("Bearer ", "");
    }

    private Jws<Claims> generateClaimsFromJwtToken(String jwt) {
        return Jwts.parserBuilder().setSigningKey(tokenKey.getBytes()).build().parseClaimsJws(jwt);
    }

    private JwtContent extract(Jws<Claims> jwsClaims) {
        final String roleName = jwsClaims.getBody().get("role", String.class);
        final GrantedAuthority authority = new SimpleGrantedAuthority(roleName);

        return JwtContent.builder()
                .username(jwsClaims.getBody().getSubject())
                .authority(authority)
                .expiration(jwsClaims.getBody().getExpiration())
                .issuer( jwsClaims.getBody().getIssuer())
                .build();
    }

    private boolean isValidToken(JwtContent jwtContent) {
        final String username = jwtContent.getUsername();
        final String issuer = jwtContent.getIssuer();

        return (!isEmpty(username) &&
                issuer.equals(this.issuer) &&
                expiration.after(new Date(System.currentTimeMillis())));
    }

    @Getter
    @Builder
    private static class JwtContent {

        private String username;

        private String issuer;

        private Date expiration;

        private GrantedAuthority authority;

    }

}