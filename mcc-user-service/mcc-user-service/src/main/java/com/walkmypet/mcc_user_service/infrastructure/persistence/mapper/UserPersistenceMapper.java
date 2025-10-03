package com.walkmypet.mcc_user_service.infrastructure.persistence.mapper;

import com.walkmypet.mcc_user_service.domain.model.RoleDO;
import com.walkmypet.mcc_user_service.domain.model.UserDO;
import com.walkmypet.mcc_user_service.infrastructure.persistence.entity.Role;
import com.walkmypet.mcc_user_service.infrastructure.persistence.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

  @Mapping(source = "role", target = "role")
  User toEntity(UserDO user);

  @Mapping(source = "role", target = "role")
  UserDO toDomain(User userEntity);


  Role toEntity(RoleDO roleDO);
  RoleDO toDomain(Role role);
}