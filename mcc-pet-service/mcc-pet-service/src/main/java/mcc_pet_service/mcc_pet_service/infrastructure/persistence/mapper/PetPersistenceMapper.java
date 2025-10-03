package mcc_pet_service.mcc_pet_service.infrastructure.persistence.mapper;

import mcc_pet_service.mcc_pet_service.domain.model.PetDO;
import mcc_pet_service.mcc_pet_service.infrastructure.persistence.entity.Pet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetPersistenceMapper {
  Pet toEntity(PetDO petDO);

  PetDO toDomain(Pet pet);
}
