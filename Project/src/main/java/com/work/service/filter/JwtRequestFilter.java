package com.work.service.filter;

import com.work.service.util.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        Long userId = null;
        String jwt = null;
        logger.info("Request URL: " + request.getRequestURL());
        logger.info("Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);//提取授权头中的JWT令牌，去掉前七个字符
            try {
                userId = jwtUtil.getUserIdFromToken(jwt);//从JWT令牌中获取用户名
                logger.info("Extracted userId: " + userId);
            } catch (Exception e) {
                logger.warn("Unable to parse JWT token: " + e.getMessage());
            }
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 验证令牌
            if (jwtUtil.validateToken(jwt, userId)) {
                //创建一个UsernamePasswordAuthenticationToken对象，并设置用户名和权限
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Authentication set for userId: " + userId);
            } else {
                logger.warn("Token validation failed for userId: " + userId);
            }
        }
        filterChain.doFilter(request, response);
    }

}