package com.walkmypet.mcc_user_service.infrastructure.web;

import com.walkmypet.mcc_user_service.domain.model.UserDO;
import com.walkmypet.mcc_user_service.domain.port.in.UserServicePort;
import com.walkmypet.mcc_user_service.dto.LoginRequestDTO;
import com.walkmypet.mcc_user_service.dto.LoginResponseDTO;
import com.walkmypet.mcc_user_service.infrastructure.security.BCryptPasswordEncoderAdapter;
import com.walkmypet.mcc_user_service.infrastructure.security.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


@RestController
@Slf4j
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

  private final JwtUtil jwtUtil;
  private final UserServicePort userServicePort;
  private final BCryptPasswordEncoderAdapter bCryptPasswordEncoderAdapter;

  public AuthController(JwtUtil jwtUtil, UserServicePort userServicePort, BCryptPasswordEncoderAdapter bCryptPasswordEncoderAdapter) {
    this.jwtUtil = jwtUtil;
    this.userServicePort = userServicePort;
    this.bCryptPasswordEncoderAdapter = bCryptPasswordEncoderAdapter;
  }

  /**
   * POST /auth/login:
   * - Validates the user credentials (email and password).
   * - If the credentials are correct:
   * - Generates a JWT token containing:
   * - role: the user's role.
   * - userId: the user's identifier.
   * - Returns 200 OK with a LoginResponseDTO (userId, fullName, email, token).
   * - If the credentials are invalid:
   * - Returns 401 Unauthorized with an error message.
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
    log.info("Login request received");
    // Look up user by email
    Optional<UserDO> optionalUser = userServicePort.findByEmail(request.getEmail());
    if (optionalUser.isPresent()) {
      UserDO user = optionalUser.get();
      // Validate password
      if (bCryptPasswordEncoderAdapter.matches(request.getPassword(), user.getPassword())) {
        // Generate token
        Map<String, Object> claims = Map.of(
          "role", user.getRole().getName(),
          "userId", user.getId()
        );
        String token = jwtUtil.generateToken(user.getEmail(), claims);
        System.out.println("Claims: " + claims);


        LoginResponseDTO response = LoginResponseDTO.builder()
          .userId(user.getId())
          .fullName(user.getFullName())
          .email(user.getEmail())
          .token(token)
          .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
      }
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
  }
}
