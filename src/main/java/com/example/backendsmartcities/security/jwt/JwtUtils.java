package com.example.backendsmartcities.security.jwt;

import com.example.backendsmartcities.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
/**
 * Author: Badreddine TIRGANI
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  


    @Value("${smarCities.app.jwtSecret}")
    private String jwtSecret;

    @Value("${smarCities.app.EXPIRE_ACCESS_TOKEN}")
    private int EXPIRE_ACCESS_TOKEN;

    @Value("${smarCities.app.EXPIRE_REFRESH_TOKEN}")
    private int EXPIRE_REFRESH_TOKEN;

    @Value("${smarCities.app.jwtCookieName}")
    private String jwtCookie;

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("username").toString();
    }
    public String generateRefreshJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userPrincipal.getId());
        claims.put("email", userPrincipal.getEmail());
        claims.put("username", userPrincipal.getUsername());
        claims.put("nonce", UUID.randomUUID().toString()); // Add a nonce to prevent replay attacks
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRE_REFRESH_TOKEN))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    private static final String jwtCookieName = "JWT";

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookieName, "")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
                .build();
    }





    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userPrincipal.getId());
        claims.put("email", userPrincipal.getEmail());
        claims.put("username", userPrincipal.getUsername());
        claims.put("nonce", UUID.randomUUID().toString()); // Add a nonce to prevent replay attacks
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + EXPIRE_ACCESS_TOKEN))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }





    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


    public ResponseCookie createJwtCookie(String token) {
        return ResponseCookie.from(jwtCookie, token)
                .httpOnly(true)
                .maxAge(EXPIRE_ACCESS_TOKEN)
                .path("/")
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie createRefreshCookie(String token) {
        return ResponseCookie.from(jwtCookie, token)
                .httpOnly(true)
                .maxAge(EXPIRE_REFRESH_TOKEN)
                .path("/")
                .sameSite("Strict")
                .build();
    }


}