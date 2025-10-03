package com.walkmypet.mcc_user_service.dto;


import com.walkmypet.mcc_user_service.domain.model.RoleDO;
import lombok.Data;


@Data
public class UserResponseDTO {
  private Long id;
  private String email;
  private String fullName;
  private RoleDO role;
}
