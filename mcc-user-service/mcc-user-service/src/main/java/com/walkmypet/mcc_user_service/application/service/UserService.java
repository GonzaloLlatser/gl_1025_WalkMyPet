package com.walkmypet.mcc_user_service.application.service;

import com.walkmypet.mcc_user_service.domain.model.UserDO;
import com.walkmypet.mcc_user_service.domain.port.in.PasswordEncoderPort;
import com.walkmypet.mcc_user_service.domain.port.in.UserServicePort;
import com.walkmypet.mcc_user_service.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServicePort {

  private final UserRepositoryPort userRepositoryPort;
  private final PasswordEncoderPort passwordEncoderPort;

  public UserService(UserRepositoryPort userRepositoryPort, PasswordEncoderPort passwordEncoderPort) {
    this.userRepositoryPort = userRepositoryPort;
    this.passwordEncoderPort = passwordEncoderPort;
  }

  @Override
  public UserDO add(UserDO user) {
    String hashedPassword = passwordEncoderPort.encode(user.getPassword());
    user.setPassword(hashedPassword);
    return userRepositoryPort.save(user);
  }

  @Override
  public List<UserDO> getAll() {
    return userRepositoryPort.getAll();
  }

  @Override
  public UserDO getById(Long id) {
    return userRepositoryPort.getUserById(id);
  }

  @Override
  public boolean delete(Long id) {
    return userRepositoryPort.deleteById(id);
  }

  @Override
  public Optional<UserDO> findByEmail(String email) {
    return userRepositoryPort.findByEmail(email);
  }
}
