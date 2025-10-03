package mcc_walkreservation_service.mcc_walkreservation_service.domain.port.in;

import mcc_walkreservation_service.mcc_walkreservation_service.domain.model.WalkReservationDO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WalkReservationServicePort {

  WalkReservationDO add(WalkReservationDO walkReservation);

  List<WalkReservationDO> getAll();

  WalkReservationDO getById(Long id);

  boolean delete(Long id);

  List<WalkReservationDO> findByWalkerId(long l);
}
