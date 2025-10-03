package com.walkmypet.mcc_user_service.domain.port.out;

import com.walkmypet.mcc_user_service.domain.model.UserDO;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
  UserDO save(UserDO user);

  List<UserDO> getAll();

  UserDO getUserById(Long id);

  boolean deleteById(Long id);

  Optional<UserDO> findByEmail(String email);
}
