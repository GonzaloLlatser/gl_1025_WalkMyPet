package mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "walk_reservations")
public class WalkReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "pet_id", nullable = false)
  private Long petId;

  @Column(name = "walker_id", nullable = false)
  private Long walkerId;

  @Column(name = "start_walk_time", nullable = false)
  private LocalDateTime startWalkTime;

  @Column(name = "end_walk_time", nullable = false)
  private LocalDateTime endWalkTime;
}




