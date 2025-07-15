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
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @Autowired
    public JwtRequestFilter(JwtUtil jwtUtil, @Lazy JwtService jwtService) {
        this.jwtUtil = jwtUtil;
        this.jwtService = jwtService;
        logger.info("JwtRequestFilter initialized");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        logger.info("Processing request to: {}", request.getRequestURI());
        
        // Skip filter for public endpoints
        if (shouldNotFilter(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Extract token from header
        if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) {
            logger.warn("JWT Token is missing or does not begin with Bearer String");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No JWT token found in request headers");
            return;
        }
        
        jwtToken = requestTokenHeader.substring(7);
        logger.debug("JWT Token received: {}", 
            jwtToken.length() > 10 ? 
                jwtToken.substring(0, 5) + "..." + jwtToken.substring(jwtToken.length() - 5) : 
                "[token too short]");
                
        try {
            username = jwtUtil.getUsernameFromToken(jwtToken);
            logger.info("Successfully extracted username from token: {}", username);
        } catch (IllegalArgumentException e) {
            logger.error("Unable to get JWT Token: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
            return;
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token has expired: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
            return;
        } catch (Exception e) {
            logger.error("Error processing JWT Token: " + e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing authentication");
            return;
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
