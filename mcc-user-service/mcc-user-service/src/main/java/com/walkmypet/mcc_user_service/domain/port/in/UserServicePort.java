package com.walkmypet.mcc_user_service.domain.port.in;

import com.walkmypet.mcc_user_service.domain.model.UserDO;
import com.walkmypet.mcc_user_service.infrastructure.persistence.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServicePort {
  UserDO add(UserDO user);

  List<UserDO> getAll();

  UserDO getById(Long id);

  boolean delete(Long id);

  Optional<UserDO> findByEmail(String email);
}
