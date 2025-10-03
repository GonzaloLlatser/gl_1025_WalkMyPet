package com.walkmypet.mcc_user_service.dto;

import com.walkmypet.mcc_user_service.domain.model.RoleDO;
import lombok.Data;

@Data
public class UserRequestDTO {
  private String email;
  private String password;
  private String fullName;
  private RoleDO role;
}