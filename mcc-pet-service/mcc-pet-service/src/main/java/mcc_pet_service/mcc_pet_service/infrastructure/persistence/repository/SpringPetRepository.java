package mcc_pet_service.mcc_pet_service.infrastructure.persistence.repository;

import mcc_pet_service.mcc_pet_service.infrastructure.persistence.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringPetRepository extends JpaRepository<Pet, Long> {
  List<Pet> findByOwnerId(long id);
}
