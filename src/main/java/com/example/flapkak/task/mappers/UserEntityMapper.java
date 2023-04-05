package com.example.flapkak.task.mappers;

import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component

public class UserEntityMapper {
    public UserEntity userEntity(CreateUserDto createUserDto){
        UserEntity userEntity = UserEntity.builder()
                .username(createUserDto.getUsername())
                .role(createUserDto.getRole())
                .password(createUserDto.getPassword())
                .build();
        return userEntity;
    }
}
