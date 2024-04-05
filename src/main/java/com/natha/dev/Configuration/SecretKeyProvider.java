package com.natha.dev.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class SecretKeyProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String getJwtSecret() {
        return jwtSecret;
    }

}
