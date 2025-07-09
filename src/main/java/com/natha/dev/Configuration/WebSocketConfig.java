package com.natha.dev.Configuration;

import com.natha.dev.ServiceImpl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtService jwtService;

    @Override
    public void configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")
                //.addInterceptors(jwtHandshakeInterceptor)  // Si w vle verifye token nan ChannelInterceptor, ou ka retire entèseptè sa.
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                System.out.println("STOMP Command: " + accessor.getCommand());

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authHeader = accessor.getFirstNativeHeader("Authorization");
                    System.out.println("Authorization header: " + authHeader);

                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        System.out.println("No Bearer token in STOMP CONNECT");
                        throw new IllegalArgumentException("No Bearer token in STOMP CONNECT");
                    }
                    String token = authHeader.substring(7);
                    System.out.println("Token: " + token);

                    if (!jwtService.validateToken(token)) {
                        System.out.println("Invalid JWT Token");
                        throw new IllegalArgumentException("Invalid JWT Token");
                    }
                    String username = jwtService.extractUsername(token);
                    System.out.println("User connected: " + username);
                    accessor.setUser(() -> username);
                }
                return message;
            }
        });
    }


}
