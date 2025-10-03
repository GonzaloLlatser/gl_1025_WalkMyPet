package mcc_walkreservation_service.mcc_walkreservation_service.application.service;

import mcc_walkreservation_service.mcc_walkreservation_service.domain.model.WalkReservationDO;
import mcc_walkreservation_service.mcc_walkreservation_service.domain.port.in.WalkReservationServicePort;
import mcc_walkreservation_service.mcc_walkreservation_service.domain.port.out.WalkReservationRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class walkreservationService implements WalkReservationServicePort {

  private final WalkReservationRepositoryPort walkReservationRepositoryPort;

  public walkreservationService(WalkReservationRepositoryPort walkReservationRepositoryPort) {
    this.walkReservationRepositoryPort = walkReservationRepositoryPort;
  }

  @Override
  public WalkReservationDO add(WalkReservationDO walkReservation) {
    return walkReservationRepositoryPort.add(walkReservation);
  }

  @Override
  public List<WalkReservationDO> getAll() {
    return walkReservationRepositoryPort.getAll();
  }

  @Override
  public WalkReservationDO getById(Long id) {
    return walkReservationRepositoryPort.getById(id);
  }

  @Override
  public boolean delete(Long id) {
    return walkReservationRepositoryPort.delete(id);
  }

  @Override
  public List<WalkReservationDO> findByWalkerId(long l) {
    return walkReservationRepositoryPort.findByWalkerId(l);
  }
}
