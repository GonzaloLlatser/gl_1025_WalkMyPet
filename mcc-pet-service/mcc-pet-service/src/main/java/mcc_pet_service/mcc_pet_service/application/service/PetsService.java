package mcc_pet_service.mcc_pet_service.application.service;

import mcc_pet_service.mcc_pet_service.domain.model.PetDO;
import mcc_pet_service.mcc_pet_service.domain.port.in.PetServicePort;
import mcc_pet_service.mcc_pet_service.domain.port.out.PetRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetsService implements PetServicePort {

  private final PetRepositoryPort petRepositoryPort;

  public PetsService(PetRepositoryPort petRepositoryPort) {
    this.petRepositoryPort = petRepositoryPort;
  }


  @Override
  public PetDO add(PetDO pet) {
    return petRepositoryPort.add(pet);
  }

  @Override
  public List<PetDO> getAll() {
    return petRepositoryPort.getAll();
  }

  @Override
  public PetDO getById(Long id) {
    return petRepositoryPort.getById(id);
  }

  @Override
  public boolean delete(Long id) {
    return petRepositoryPort.delete(id);
  }

  @Override
  public List<PetDO> findByOwnerId(long id) {
    return petRepositoryPort.findByOwnerId(id);
  }
}
