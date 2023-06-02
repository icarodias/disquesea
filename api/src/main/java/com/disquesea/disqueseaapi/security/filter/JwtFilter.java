package com.disquesea.disqueseaapi.security.filter;

import com.disquesea.disqueseaapi.controllers.exception.handler.Problem;
import com.disquesea.disqueseaapi.security.components.TokenJwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;
import static org.hibernate.internal.util.StringHelper.isEmpty;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final TokenJwtUtil tokenJwtUtil;

    public JwtFilter(TokenJwtUtil tokenJwtUtil) {
        this.tokenJwtUtil = tokenJwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!isEmpty(request.getHeader("Authorization"))) {
            final Authentication authentication = tokenJwtUtil.decodeToken(request);

            if (isNull(authentication)) {
                improvingResponse(response);
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private void improvingResponse(HttpServletResponse response) throws IOException{
        final int status = HttpStatus.UNAUTHORIZED.value();

        final Problem body = Problem.builder()
                .detail("Invalid token")
                .title("Unauthorized User")
                .status(status)
                .type("/unauthorized-user")
                .build();

        ObjectMapper mapper = new ObjectMapper();

        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().print(mapper.writeValueAsString(body));
        response.getWriter().flush();
    }

}
