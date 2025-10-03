package com.walkmypet.mcc_user_service.infrastructure.web;

import com.walkmypet.mcc_user_service.domain.model.UserDO;
import com.walkmypet.mcc_user_service.domain.port.in.UserServicePort;
import com.walkmypet.mcc_user_service.dto.UserRequestDTO;
import com.walkmypet.mcc_user_service.dto.UserResponseDTO;
import com.walkmypet.mcc_user_service.infrastructure.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "Endpoints for managing users")
public class UserController {

  private final UserServicePort userServicePort;
  private final UserMapper userMapper;

  public UserController(UserServicePort userServicePort, UserMapper userMapper) {
    this.userServicePort = userServicePort;
    this.userMapper = userMapper;
  }

  /**
   * Create a new user.
   */
  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequestDTO) {

    // Map incoming DTO to domain object
    UserDO user = userMapper.toModel(userRequestDTO);

    // Call service to create user and map result back to DTO
    UserResponseDTO userResponseDTO = userMapper.toDTO(userServicePort.add(user));

    return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
  }

  /**
   * Get all user accessible by role:
   * - ADMIN: all user.
   * - OWNER: only their own user.
   * - Other roles: forbidden.
   */
  @GetMapping
  public ResponseEntity<?> getUsers(@RequestHeader("X-ROLE") String role,
                                    @RequestHeader("X-USER-ID") String userId) {

    return switch (role) {
      case "ADMIN" -> ResponseEntity.ok(userServicePort.getAll());
      case "OWNER" -> ResponseEntity.ok(userServicePort.getById(Long.parseLong(userId)));
      default ->
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
    };
  }

  /**
   * Get a user by ID.
   * - ADMIN: can access any user.
   * - OWNER: can only access their own.
   * - Other roles: forbidden.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id,
                                       @RequestHeader("X-ROLE") String role,
                                       @RequestHeader("X-USER-ID") String userId) {

    // Fetch user by ID from the service
    UserDO user = userServicePort.getById(id);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }
    UserResponseDTO userResponseDTO = userMapper.toDTO(user);

    return switch (role) {
      case "ADMIN" -> ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
      case "OWNER" -> {
        if (userResponseDTO.getId().equals(Long.parseLong(userId))) {
          yield ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
        } else {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
      }
      default ->
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
    };
  }


  /**
   * Update a user by ID.
   * - ADMIN/WALKER: can update any user.
   * - OWNER: can only update their own.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO, @RequestHeader("X-ROLE") String role,
                                      @RequestHeader("X-USER-ID") String userId) {


    // Map incoming DTO to domain object
    UserDO userToUpdate = userMapper.toModel(userRequestDTO);
    userToUpdate.setId(id);

    if (role.equals("OWNER")) {
      UserDO existingUser = userServicePort.getById(id);
      if (existingUser == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
      }
      if (!existingUser.getId().equals(Long.parseLong(userId))) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to update this resource");
      }
    }

    UserDO updatedUser = userServicePort.add(userToUpdate);
    UserResponseDTO userResponseDTO = userMapper.toDTO(updatedUser);

    return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
  }

  /**
   * Delete a user by ID.
   * - ADMIN/WALKER: can delete any user.
   * - OWNER: can only delete their own.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader("X-ROLE") String role,
                                      @RequestHeader("X-USER-ID") String userId) {

    UserDO existingUser = userServicePort.getById(id);
    if (role.equals("OWNER")) {
      if (existingUser == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
      }
      if (!existingUser.getId().equals(Long.parseLong(userId))) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to delete this resource");
      }
    }
    boolean deleted = userServicePort.delete(id);
    if (deleted) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
  }
}


