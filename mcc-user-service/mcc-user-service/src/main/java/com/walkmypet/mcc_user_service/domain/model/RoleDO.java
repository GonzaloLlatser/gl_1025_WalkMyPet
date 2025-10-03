package com.walkmypet.mcc_user_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDO {
  private Long id;
  private String name;
  private String permissionDescription;
}
