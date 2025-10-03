package com.walkmypet.mcc_user_service.domain.port.in;

public interface PasswordEncoderPort {
  String encode(String rawPassword);
  boolean matches(String rawPassword, String encodedPassword);
}
