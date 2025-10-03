package com.walkmypet.mcc_user_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {
  private Long id;
  private String password;
  private String fullName;
  private String email;
  private RoleDO role;
}
