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

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);//提取授权头中的JWT令牌，去掉前七个字符
            try {
                userId = jwtUtil.getUserIdFromToken(jwt);//从JWT令牌中获取用户名
            } catch (Exception e) {
                logger.warn("Unable to parse JWT token: " + e.getMessage());
            }
        }
        /**
         * 验证令牌
         * 确保用户名不为空且当前用户未进行身份验证
         * 调用JwtUtil的validateToken方法验证令牌，并返回一个布尔值
         * 如果令牌有效，则创建一个UsernamePasswordAuthenticationToken对象，并设置用户名和权限
         * 将此对象设置为当前用户的身份验证，并存入Spring Security上下文
         */
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 验证令牌
            if (jwtUtil.validateToken(jwt, userId)) {
                //创建一个UsernamePasswordAuthenticationToken对象，并设置用户名和权限
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}