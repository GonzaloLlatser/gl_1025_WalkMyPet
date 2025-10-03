package mcc_pet_service.mcc_pet_service.dto;

import lombok.Data;

@Data
public class PetResponseDTO {
  private Long id;
  private String name;
  private String race;
  private int age;
  private Long ownerId;
}