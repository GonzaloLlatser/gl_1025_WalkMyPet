package mcc_walkreservation_service.mcc_walkreservation_service.domain.port.out;

import mcc_walkreservation_service.mcc_walkreservation_service.domain.model.WalkReservationDO;

import java.util.List;

public interface WalkReservationRepositoryPort {
  WalkReservationDO add(WalkReservationDO walkReservation);

  List<WalkReservationDO> getAll();

  WalkReservationDO getById(Long id);

  boolean delete(Long id);

  List<WalkReservationDO> findByWalkerId(long l);
}
