package mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import mcc_walkreservation_service.mcc_walkreservation_service.domain.model.WalkReservationDO;
import mcc_walkreservation_service.mcc_walkreservation_service.domain.port.in.WalkReservationServicePort;
import mcc_walkreservation_service.mcc_walkreservation_service.dto.WalkRequestDTO;
import mcc_walkreservation_service.mcc_walkreservation_service.dto.WalkResponseDTO;
import mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.mapper.WalkMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/walk_reservations")
@Tag(name = "Walk Reservation controller", description = "Endpoints for managing walk reservations")
public class WalkReservationController {

  private final WalkReservationServicePort walkReservationServicePort;
  private final WalkMapper walkMapper;

  public WalkReservationController(WalkReservationServicePort walkReservationServicePort, WalkMapper walkMapper) {
    this.walkReservationServicePort = walkReservationServicePort;
    this.walkMapper = walkMapper;
  }

  /**
   * Post walks accessible by role:
   * - ADMIN: all walks.
   * - OWNER: only their own user.
   * - Other roles: forbidden.
   */
  @PostMapping
  public ResponseEntity<?> createWalk(@RequestBody WalkRequestDTO walkRequestDTO) {
    WalkResponseDTO walkResponseDTO = walkMapper.toDTO(walkReservationServicePort.add(walkMapper.toModel(walkRequestDTO)));
    return ResponseEntity.status(HttpStatus.CREATED).body(walkResponseDTO);
  }

  /**
   * Get all walks accessible by role:
   * - ADMIN: all walks.
   * - OWNER: only their own user.
   * - Other roles: forbidden.
   */
  @GetMapping
  public ResponseEntity<?> getWalks(@RequestHeader("X-ROLE") String role,
                                    @RequestHeader("X-USER-ID") String userId) {

    log.info("Get walks request received with role: {} and userId: {}", role, userId);

    return switch (role) {
      case "ADMIN" -> ResponseEntity.ok(walkReservationServicePort.getAll());
      case "OWNER" -> ResponseEntity.ok(walkReservationServicePort.findByWalkerId(Long.parseLong(userId)));
      default ->
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
    };
  }

  /**
   * Get walks accessible by role:
   * - ADMIN: all walks.
   * - OWNER: only their own user.
   * - Other roles: forbidden.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getWalkById(@PathVariable Long id,
                                       @RequestHeader("X-ROLE") String role,
                                       @RequestHeader("X-USER-ID") String userId) {

    log.info("Get walk by ID request received with role: {} and userId: {}", role, userId);

    WalkReservationDO walk = walkReservationServicePort.getById(id);

    if (walk == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Walk reservation not found");
    }
    return switch (role) {
      case "ADMIN" -> ResponseEntity.ok(walk);
      case "OWNER" -> {
        if (walk.getWalkerId().equals(Long.parseLong(userId))) {
          yield ResponseEntity.ok(walk);
        } else {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
      }
      default ->
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
    };
  }

  /**
   * Delete all walks accessible by role:
   * - ADMIN: all walks.
   * - OWNER: only their own user.
   * - Other roles: forbidden.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteWalk(@PathVariable Long id,
                                      @RequestHeader("X-ROLE") String role,
                                      @RequestHeader("X-USER-ID") String userId) {

    log.info("Delete walk by ID request received with role: {} and userId: {}", role, userId);

    WalkReservationDO walk = walkReservationServicePort.getById(id);
    if (walk == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Walk reservation not found");
    }
    return switch (role) {
      case "ADMIN" -> {
        walkReservationServicePort.delete(id);
        yield ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      }
      case "OWNER" -> {
        if (walk.getWalkerId().equals(Long.parseLong(userId))) {
          walkReservationServicePort.delete(id);
          yield ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
      }
      default ->
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
    };
  }

  /**
   * Put walks accessible by role:
   * - ADMIN: all walks.
   * - OWNER: only their own user.
   * - Other roles: forbidden.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateWalk(@PathVariable Long id,
                                      @RequestBody WalkRequestDTO walkRequestDTO,
                                      @RequestHeader("X-ROLE") String role,
                                      @RequestHeader("X-USER-ID") String userId) {

    log.info("Update walk by ID request received with role: {} and userId: {}", role, userId);

    WalkReservationDO existingWalk = walkReservationServicePort.getById(id);
    if (existingWalk == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Walk reservation not found");
    }
    return switch (role) {
      case "ADMIN" -> {
        WalkReservationDO walkToUpdate = walkMapper.toModel(walkRequestDTO);
        walkToUpdate.setId(id);
        WalkResponseDTO walkResponseDTO = walkMapper.toDTO(walkReservationServicePort.add(walkToUpdate));
        yield ResponseEntity.ok(walkResponseDTO);
      }
      case "OWNER" -> {
        if (existingWalk.getWalkerId().equals(Long.parseLong(userId))) {
          WalkReservationDO walkToUpdate = walkMapper.toModel(walkRequestDTO);
          walkToUpdate.setId(id);
          WalkResponseDTO walkResponseDTO = walkMapper.toDTO(walkReservationServicePort.add(walkToUpdate));
          yield ResponseEntity.ok(walkResponseDTO);
        } else {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
      }
      default ->
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
    };
  }
}
