package com.walkmypet.mcc_user_service.infrastructure.persistence.adapter;

import com.walkmypet.mcc_user_service.domain.model.UserDO;
import com.walkmypet.mcc_user_service.domain.port.out.UserRepositoryPort;
import com.walkmypet.mcc_user_service.infrastructure.persistence.entity.User;
import com.walkmypet.mcc_user_service.infrastructure.persistence.mapper.UserPersistenceMapper;
import com.walkmypet.mcc_user_service.infrastructure.persistence.repository.SpringUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

  private final SpringUserRepository springUserRepository;
  private final UserPersistenceMapper userPersistenceMapper;

  public UserRepositoryAdapter(SpringUserRepository springUserRepository, UserPersistenceMapper userPersistenceMapper) {
    this.springUserRepository = springUserRepository;
    this.userPersistenceMapper = userPersistenceMapper;
  }

  @Override
  public UserDO save(UserDO user) {
    User userEntity = userPersistenceMapper.toEntity(user);

    return userPersistenceMapper.toDomain(springUserRepository.save(userEntity));
  }

  @Override
  public List<UserDO> getAll() {
    return springUserRepository.findAll()
      .stream().map(userPersistenceMapper::toDomain)
      .toList();
  }

  @Override
  public UserDO getUserById(Long id) {
    return springUserRepository.findById(id)
      .map(userPersistenceMapper::toDomain)
      .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
  }

  @Override
  public boolean deleteById(Long id) {
    if (springUserRepository.existsById(id)) {
      springUserRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Override
  public Optional<UserDO> findByEmail(String email) {
    return springUserRepository.findByEmail(email)
      .map(userPersistenceMapper::toDomain);
  }
}

