package com.natha.dev.Configuration;

import com.natha.dev.ServiceImpl.JwtService; // ✅ Sèl sa ou bezwen

import com.natha.dev.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtService jwtService; // Rete menm si li soti nan ServiceImpl


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String token = null;

        HttpHeaders headers = request.getHeaders();
        String authHeader = headers.getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null) {
            URI uri = request.getURI();
            String query = uri.getQuery();
            if (query != null) {
                Optional<String> tokenParam = Arrays.stream(query.split("&"))
                        .filter(param -> param.startsWith("token="))
                        .map(param -> param.substring("token=".length()))
                        .findFirst();
                if (tokenParam.isPresent()) {
                    token = tokenParam.get();
                }
            }
        }

        if (token != null && jwtService.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);

            // Kreye yon Authentication obj pou mete nan attributes
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))); // ajoute wòl ou vle a

            attributes.put("principal", auth);

            return true;
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }





    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // Pa gen anyen pou fè la
    }
}
