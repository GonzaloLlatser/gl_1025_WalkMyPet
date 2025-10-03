package mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.mapper;

import mcc_walkreservation_service.mcc_walkreservation_service.domain.model.WalkReservationDO;
import mcc_walkreservation_service.mcc_walkreservation_service.dto.WalkRequestDTO;
import mcc_walkreservation_service.mcc_walkreservation_service.dto.WalkResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WalkMapper {
  WalkReservationDO toModel(WalkRequestDTO walkRequestDTO);

  WalkResponseDTO toDTO(WalkReservationDO walkReservationDO);

}