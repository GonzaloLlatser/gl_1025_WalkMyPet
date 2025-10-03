package mcc_walkreservation_service.mcc_walkreservation_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalkResponseDTO {
  private Long id;
  private Long petId;
  private Long walkerId;
  private String startWalkTime;
  private String endWalkTime;
}
