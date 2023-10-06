package com.fabmera.spring_security.mapper;

import com.fabmera.spring_security.dto.AuthenticationRequest;
import com.fabmera.spring_security.entity.UserModel;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private ModelMapper modelMapper;

    public AuthenticationRequest convertToDto(UserModel userModel) {
        return modelMapper.map(userModel, AuthenticationRequest.class);
    }

    public UserModel convertToEntity(AuthenticationRequest authenticationRequest) {
        return modelMapper.map(authenticationRequest, UserModel.class);
    }
}
