package com.walkmypet.mcc_user_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
  private Long userId;
  private String fullName;
  private String email;
  private String token;
}
