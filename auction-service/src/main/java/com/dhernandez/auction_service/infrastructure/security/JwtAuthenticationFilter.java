package com.dhernandez.auction_service.infrastructure.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;
    public JwtAuthenticationFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = request.getHeader("Authorization").substring(7);
        try {
            boolean tokenValid = jwtUtil.isTokenValid(jwtToken);
            if(!tokenValid){
                response.setStatus(401);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{ \"error\":  \"Invalid or expired token\"}");
                return;
            }
            String userId = jwtUtil.extractUserId(jwtToken);
            String role = jwtUtil.extractRole(jwtToken);

            SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(roleAuthority));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{ \"error\": \"Invalid or expired token: " + e.getMessage() + "\" }");
            return;
        }
    }
    
}
