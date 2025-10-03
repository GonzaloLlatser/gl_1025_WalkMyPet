package mcc_pet_service.mcc_pet_service.domain.port.in;

import mcc_pet_service.mcc_pet_service.domain.model.PetDO;

import java.util.List;

public interface PetServicePort {
  PetDO add(PetDO pet);

  List<PetDO> getAll();

  PetDO getById(Long id);

  boolean delete(Long id);

  List<PetDO> findByOwnerId(long l);
}
