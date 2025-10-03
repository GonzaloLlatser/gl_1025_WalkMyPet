package mcc_pet_service.mcc_pet_service.infrastructure.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import mcc_pet_service.mcc_pet_service.domain.model.PetDO;
import mcc_pet_service.mcc_pet_service.domain.port.in.PetServicePort;
import mcc_pet_service.mcc_pet_service.dto.PetRequestDTO;
import mcc_pet_service.mcc_pet_service.dto.PetResponseDTO;
import mcc_pet_service.mcc_pet_service.infrastructure.mapper.PetMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pets")
@Tag(name = "Pets controller ", description = "Endpoints for managing pets")
public class PetController {

  private final PetServicePort petServicePort;
  private final PetMapper petMapper;

  public PetController(PetServicePort petServicePort, PetMapper petMapper) {
    this.petServicePort = petServicePort;
    this.petMapper = petMapper;
  }

  /**
   * Create a new pet.
   * - OWNER can only create pets for themselves.
   * - ADMIN/WALKER can create pets for any user (based on the DTO).
   */
  @PostMapping
  public ResponseEntity<?> createPet(@RequestBody PetRequestDTO petRequestDTO,
                                     @RequestHeader("X-ROLE") String role,
                                     @RequestHeader("X-USER-ID") String userId) {

    PetDO pet = petMapper.toModel(petRequestDTO);

    if ("OWNER".equals(role)) {
      pet.setOwnerId(Long.parseLong(userId));
    }

    PetResponseDTO responseDTO = petMapper.toDTO(petServicePort.add(pet));
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  /**
   * Get all pets accessible by role:
   * - ADMIN: all pets.
   * - OWNER: only their own pets.
   * - Other roles: forbidden.
   */
  @GetMapping
  public List<PetDO> getPets(@RequestHeader("X-ROLE") String role,
                             @RequestHeader("X-USER-ID") String userId) {

    return switch (role) {
      case "ADMIN" -> petServicePort.getAll();
      case "OWNER" -> petServicePort.findByOwnerId(Long.parseLong(userId));
      default ->
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
    };
  }


  /**
   * Get a pet by ID.
   * - ADMIN: can access any pet.
   * - OWNER: can only access their own pet.
   * - Other roles: forbidden.
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getPetById(@PathVariable Long id,
                                      @RequestHeader("X-ROLE") String role,
                                      @RequestHeader("X-USER-ID") String userId) {

    log.info("Fetching pet with ID: {}", id);

    PetDO pet = petServicePort.getById(id);

    if (pet == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
    }

    PetResponseDTO petResponseDTO = petMapper.toDTO(pet);

    return switch (role) {
      case "ADMIN" -> ResponseEntity.status(HttpStatus.OK).body(petResponseDTO);
      case "OWNER" -> {
        if (pet.getOwnerId().equals(Long.parseLong(userId))) {
          yield ResponseEntity.status(HttpStatus.OK).body(petResponseDTO);
        } else {
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
        }
      }
      default ->
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to access this resource");
    };
  }

  /**
   * Update a pet by ID.
   * - ADMIN/WALKER: can update any pet.
   * - OWNER: can only update their own pet.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updatePet(@PathVariable Long id, @RequestBody PetRequestDTO petRequestDTO, @RequestHeader("X-ROLE") String role,
                                     @RequestHeader("X-USER-ID") String userId) {

    PetDO petToUpdate = petMapper.toModel(petRequestDTO);
    petToUpdate.setId(id);

    if (role.equals("OWNER")) {
      PetDO existingPet = petServicePort.getById(id);
      if (existingPet == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found for update");
      }
      if (!existingPet.getOwnerId().equals(Long.parseLong(userId))) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this pet");
      }
      petToUpdate.setOwnerId(existingPet.getOwnerId());
    }

    PetDO updatedPet = petServicePort.add(petToUpdate);
    PetResponseDTO petResponseDTO = petMapper.toDTO(updatedPet);
    return ResponseEntity.status(HttpStatus.OK).body(petResponseDTO);
  }

  /**
   * Delete a pet by ID.
   * - ADMIN/WALKER: can delete any pet.
   * - OWNER: can only delete their own pet.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePet(@PathVariable Long id,
                                     @RequestHeader("X-ROLE") String role,
                                     @RequestHeader("X-USER-ID") String userId) {
    PetDO existingPet = petServicePort.getById(id);

    if (role.equals("OWNER")) {
      if (existingPet == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found for deletion");
      }
      if (!existingPet.getOwnerId().equals(Long.parseLong(userId))) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this pet");
      }
    }

    boolean deleted = petServicePort.delete(id);
    if (!deleted) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found");
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}