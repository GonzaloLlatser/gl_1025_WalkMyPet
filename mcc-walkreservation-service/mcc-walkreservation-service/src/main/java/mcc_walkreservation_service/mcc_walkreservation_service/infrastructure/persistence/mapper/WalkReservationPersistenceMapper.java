package mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.persistence.mapper;

import mcc_walkreservation_service.mcc_walkreservation_service.domain.model.WalkReservationDO;
import mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.persistence.entity.WalkReservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalkReservationPersistenceMapper {

  WalkReservation toEntity( WalkReservationDO walkReservationDO);

  WalkReservationDO toDomain(WalkReservation walkReservation);
}
