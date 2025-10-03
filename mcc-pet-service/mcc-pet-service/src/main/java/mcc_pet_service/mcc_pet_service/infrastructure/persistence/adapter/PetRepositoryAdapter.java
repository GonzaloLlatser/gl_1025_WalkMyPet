package mcc_pet_service.mcc_pet_service.infrastructure.persistence.adapter;

import mcc_pet_service.mcc_pet_service.domain.model.PetDO;
import mcc_pet_service.mcc_pet_service.domain.port.out.PetRepositoryPort;
import mcc_pet_service.mcc_pet_service.infrastructure.persistence.entity.Pet;
import mcc_pet_service.mcc_pet_service.infrastructure.persistence.mapper.PetPersistenceMapper;
import mcc_pet_service.mcc_pet_service.infrastructure.persistence.repository.SpringPetRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PetRepositoryAdapter implements PetRepositoryPort {

  private final SpringPetRepository springPetRepository;
  private final PetPersistenceMapper petPersistenceMapper;

  public PetRepositoryAdapter(SpringPetRepository springPetRepository, PetPersistenceMapper petPersistenceMapper) {
    this.springPetRepository = springPetRepository;
    this.petPersistenceMapper = petPersistenceMapper;
  }

  @Override
  public PetDO add(PetDO pet) {
    Pet petEntity = petPersistenceMapper.toEntity(pet);

    return petPersistenceMapper.toDomain(springPetRepository.save(petEntity));
  }

  @Override
  public List<PetDO> getAll() {
    return springPetRepository.findAll()
      .stream().map(petPersistenceMapper::toDomain)
      .toList();
  }

  @Override
  public PetDO getById(Long id) {
    return springPetRepository.findById(id)
      .map(petPersistenceMapper::toDomain)
      .orElse(null);
  }

  @Override
  public boolean delete(Long id) {
    return springPetRepository.findById(id).map(pet -> {
      springPetRepository.deleteById(id);
      return true;
    }).orElse(false);
  }

  @Override
  public List <PetDO> findByOwnerId(long id) {
    return springPetRepository.findByOwnerId(id)
      .stream().map(petPersistenceMapper::toDomain)
      .toList();
  }
}
