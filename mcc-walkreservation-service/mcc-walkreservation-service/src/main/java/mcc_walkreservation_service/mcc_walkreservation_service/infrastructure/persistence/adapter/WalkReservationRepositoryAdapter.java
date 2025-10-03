package mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.persistence.adapter;

import mcc_walkreservation_service.mcc_walkreservation_service.domain.model.WalkReservationDO;
import mcc_walkreservation_service.mcc_walkreservation_service.domain.port.out.WalkReservationRepositoryPort;
import mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.persistence.entity.WalkReservation;
import mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.persistence.mapper.WalkReservationPersistenceMapper;
import mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.persistence.repository.SpringWalkReservationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WalkReservationRepositoryAdapter implements WalkReservationRepositoryPort {

  private final WalkReservationPersistenceMapper walkReservationPersistenceMapper;
  private final SpringWalkReservationRepository springWalkReservationRepository;

  public WalkReservationRepositoryAdapter(WalkReservationPersistenceMapper walkReservationPersistenceMapper, SpringWalkReservationRepository springWalkReservationRepository) {
    this.walkReservationPersistenceMapper = walkReservationPersistenceMapper;
    this.springWalkReservationRepository = springWalkReservationRepository;
  }

  @Override
  public WalkReservationDO add(WalkReservationDO walkReservationDO) {

    WalkReservation walkReservation = walkReservationPersistenceMapper.toEntity(walkReservationDO);
    return walkReservationPersistenceMapper.toDomain(springWalkReservationRepository.save(walkReservation));

  }

  @Override
  public List<WalkReservationDO> getAll() {
    return springWalkReservationRepository.findAll()
      .stream().map(walkReservationPersistenceMapper::toDomain)
      .toList();
  }

  @Override
  public WalkReservationDO getById(Long id) {
    return springWalkReservationRepository.findById(id)
      .map(walkReservationPersistenceMapper::toDomain)
      .orElse(null);
  }

  @Override
  public boolean delete(Long id) {
    return springWalkReservationRepository.findById(id).map(walkReservation -> {
        springWalkReservationRepository.deleteById(id);
        return true;
      }
    ).orElse(false);
  }

  @Override
  public List<WalkReservationDO> findByWalkerId(long l) {
    return springWalkReservationRepository.findByWalkerId(l)
      .stream().map(walkReservationPersistenceMapper::toDomain)
      .toList();
  }
}
