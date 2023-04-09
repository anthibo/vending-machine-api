package com.example.flapkak.task.mappers;

import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.dtos.UpdateUserDto;
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

    public UserEntity userEntity(Long userId, UpdateUserDto updateUserDto){
        UserEntity.UserEntityBuilder userEntityBuilder = UserEntity.builder();
        userEntityBuilder.id(userId);
        if (updateUserDto.getUsername() != null) {
            userEntityBuilder.username(updateUserDto.getUsername());
        }
        if (updateUserDto.getPassword() != null) {
            userEntityBuilder.password(updateUserDto.getPassword());
        }
        if (updateUserDto.getRole() != null) {
            userEntityBuilder.role(updateUserDto.getRole());
        }
                
        return userEntityBuilder.build();
    }
}
