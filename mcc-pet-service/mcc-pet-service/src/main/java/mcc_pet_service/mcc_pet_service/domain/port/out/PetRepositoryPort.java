package mcc_pet_service.mcc_pet_service.domain.port.out;

import mcc_pet_service.mcc_pet_service.domain.model.PetDO;

import java.util.List;

public interface PetRepositoryPort {
  PetDO add(PetDO pet);

  List<PetDO> getAll();

  PetDO getById(Long id);

  boolean delete(Long id);

  List<PetDO> findByOwnerId(long id);
}
