package com.walkmypet.mcc_user_service.infrastructure.mapper;


import com.walkmypet.mcc_user_service.domain.model.UserDO;
import com.walkmypet.mcc_user_service.dto.UserRequestDTO;
import com.walkmypet.mcc_user_service.dto.UserResponseDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDO toModel(UserRequestDTO userRequestDTO);

  UserResponseDTO toDTO(UserDO user);
}