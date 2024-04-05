package com.natha.dev.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;



public class JwtResponse {

    private Users user;
    private String jwtToken;

    public JwtResponse(Users user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
