package com.disquesea.disqueseaapi.security;

import com.disquesea.disqueseaapi.security.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final String[] ALL_ROLES = {"ADMIN", "MANAGER","EMPLOYEE"};

    private final JwtFilter jwtFilter;

    public SecurityConfiguration(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(
                (authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products").hasAnyRole(ALL_ROLES)
                        .requestMatchers(HttpMethod.POST, "/products").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/products/*").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/products/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/orders").hasAnyRole(ALL_ROLES)
                        .requestMatchers(HttpMethod.POST, "/orders").hasAnyRole(ALL_ROLES)
                        .requestMatchers(HttpMethod.DELETE, "/orders").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/wallet/reset").hasAnyRole(ALL_ROLES)
                        .requestMatchers(HttpMethod.PUT, "/wallet/reset").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                        .anyRequest().authenticated());

        http.cors(Customizer.withDefaults());

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
