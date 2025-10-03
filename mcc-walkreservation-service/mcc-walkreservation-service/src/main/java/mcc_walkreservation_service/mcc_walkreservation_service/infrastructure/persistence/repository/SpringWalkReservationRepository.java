package mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.persistence.repository;

import mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.persistence.entity.WalkReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringWalkReservationRepository extends JpaRepository<WalkReservation, Long> {
  List<WalkReservation> findByWalkerId(long l);
}
