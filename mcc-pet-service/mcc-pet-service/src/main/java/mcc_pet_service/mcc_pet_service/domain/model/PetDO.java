package mcc_pet_service.mcc_pet_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetDO {
  private Long id;
  private String name;
  private String race;
  private int age;
  private Long ownerId;
}
