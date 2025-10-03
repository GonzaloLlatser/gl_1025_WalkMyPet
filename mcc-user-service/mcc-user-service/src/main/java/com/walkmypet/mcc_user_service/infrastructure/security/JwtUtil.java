package com.walkmypet.mcc_user_service.infrastructure.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

  // Clave fija simple, 32 bytes
  private final Key key = Keys.hmacShaKeyFor("clave_fija_super_segura_32bytes!!".getBytes());


  private final long expiration = 1000 * 60 * 60; // 1 hora

  public String generateToken(String username, Map<String, Object> claims) {
    return Jwts.builder()
      .setClaims(claims)        // Solo tus claims (role, userId, etc.)
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + expiration))
      .signWith(key, SignatureAlgorithm.HS256)
      .compact();
  }


  public Claims validateToken(String token) throws JwtException {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody();
  }
}
