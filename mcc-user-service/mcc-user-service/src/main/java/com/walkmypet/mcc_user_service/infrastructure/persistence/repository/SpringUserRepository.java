package com.walkmypet.mcc_user_service.infrastructure.persistence.repository;


import com.walkmypet.mcc_user_service.infrastructure.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringUserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
}
