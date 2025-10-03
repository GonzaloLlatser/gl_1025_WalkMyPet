package mcc_api_gateway_service.mcc_api_gateway_service.filter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.List;


@Component
@Slf4j
public class LoggingGlobalFilter implements GlobalFilter {

  private final Key key = Keys.hmacShaKeyFor("clave_fija_super_segura_32bytes!!".getBytes());

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    log.info("Global filter executed for request: {}", exchange.getRequest().getURI());

    String path = exchange.getRequest().getPath().value();
    String method = exchange.getRequest().getMethod().name();

    // Public endpoints: not need to validate token
    if (isNotRestrictedURL(path, method)) {
      return chain.filter(exchange);
    }

    // Token validation
    List<String> authHeaders = exchange.getRequest().getHeaders().getOrEmpty("Authorization");
    if (authHeaders.isEmpty() || !authHeaders.get(0).startsWith("Bearer ")) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    // Extract token
    String token = authHeaders.get(0).substring(7);
    log.info("Token received: {}", token);


    try {
      // Parse and validate the token
      var claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

      // Extract custom claims
      String role = claims.get("role", String.class);
      Object userIdObj = claims.get("userId");
      String userId = (userIdObj != null) ? userIdObj.toString() : null;

      // Add custom headers to the request
      exchange = exchange.mutate()
        .request(r -> r.headers(headers -> {
          headers.add("X-ROLE", role);
          headers.add("X-USER-ID", userId);
        }))
        .build();
      // show the headers
      log.info("X-ROLE: {}, X-USER-ID: {}", role, userId);

    } catch (JwtException e) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    // Token OK, continue the filter chain
    log.info(exchange.toString());
    return chain.filter(exchange);
  }

  // Define which URLs are public (no token needed)
  private static boolean isNotRestrictedURL(String path, String method) {
    log.info("Checking if path is public: {} {}", method, path);
    return path.startsWith("/auth") || (path.equals("/users") && method.equals("POST")
    );
  }
}