package com.dhernandez.auction_service.infrastructure.security;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor{
    private final JwtUtil jwtUtil;
    public JwtHandshakeInterceptor(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
    @Override
    public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1, WebSocketHandler arg2, Exception arg3) {
    }
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        String header = request.getHeaders().getFirst("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }else{
            String jwt = header.substring(7);
            if(!jwtUtil.isTokenValid(jwt)){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }else{
                String userId = jwtUtil.extractUserId(jwt);
                attributes.put("userId", Long.parseLong(userId));
                return true;
            }
        }       
    }
}
