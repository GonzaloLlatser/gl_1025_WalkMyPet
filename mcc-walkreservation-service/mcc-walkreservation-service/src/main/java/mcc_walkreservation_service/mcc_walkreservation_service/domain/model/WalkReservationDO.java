package mcc_walkreservation_service.mcc_walkreservation_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalkReservationDO {
  private Long id;
  private Long petId;
  private Long walkerId;
  private LocalDateTime startWalkTime;
  private LocalDateTime endWalkTime;
}