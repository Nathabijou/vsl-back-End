package com.natha.dev.Configuration;

import com.natha.dev.ServiceImpl.JwtService;
import com.natha.dev.Util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // Extract token from header
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // Validate the token and set up Spring Security context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Debug log the token
                logger.debug("Validating token for user: " + username);
                
                UserDetails userDetails = jwtService.loadUserByUsername(username);
                
                // Debug log the user's authorities
                logger.info("User " + username + " has authorities: " + userDetails.getAuthorities());
                
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
                    // Get the claims from the token
                    Claims claims = jwtUtil.getAllClaimsFromToken(jwtToken);
                    
                    // Debug log the claims
                    logger.debug("JWT Claims: " + claims);
                    
                    // Get authorities from the token
                    @SuppressWarnings("unchecked")
                    List<String> roles = (List<String>) claims.get("roles");
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList());
                    
                    // Create authentication with authorities from the token
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            null, 
                            authorities
                        );
                    
                    authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    
                    // Debug log the final authorities being set
                    logger.debug("Setting authentication with authorities: " + authorities);
                    
                    // Set the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    logger.info("Successfully authenticated user: " + username + " with roles: " + userDetails.getAuthorities());
                } else {
                    logger.warn("Invalid JWT token for user: " + username);
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: ", e);
            }
        }

        filterChain.doFilter(request, response);

    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/reset") ||
                path.equals("/authenticate") ||
                path.equals("/registerNewUser") ||
                path.equals("/verify-otp") ||
                path.equals("/newPasswordRegister");
    }
}
