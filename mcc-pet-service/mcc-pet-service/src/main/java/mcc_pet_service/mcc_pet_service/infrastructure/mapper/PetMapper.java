package mcc_pet_service.mcc_pet_service.infrastructure.mapper;

import mcc_pet_service.mcc_pet_service.domain.model.PetDO;
import mcc_pet_service.mcc_pet_service.dto.PetRequestDTO;
import mcc_pet_service.mcc_pet_service.dto.PetResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {

  PetDO toModel(PetRequestDTO petRequestDTO);

  PetResponseDTO toDTO(PetDO pet);
}
