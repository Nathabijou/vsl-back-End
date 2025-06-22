package com.natha.dev.Controller;

import com.natha.dev.Model.MySystem;
import com.natha.dev.Util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class MySystemController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MySystemService mySystemService;


    @PostMapping("/systems")
    public ResponseEntity<MySystem> createSystem(@RequestBody MySystem system, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // retire "Bearer "
            String username = jwtUtil.getUsernameFromToken(token);
            system.setCreatedBy(username);
        }

        MySystem saved = mySystemService.save(system);
        return ResponseEntity.ok(saved);
    }
}
