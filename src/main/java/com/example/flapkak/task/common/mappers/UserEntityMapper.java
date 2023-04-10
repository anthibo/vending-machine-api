package com.example.flapkak.task.common.mappers;

import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.dtos.UpdateUserDto;
import com.example.flapkak.task.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component

public class UserEntityMapper {
    public UserEntity userEntity(CreateUserDto createUserDto) {
        UserEntity userEntity = UserEntity.builder()
                .username(createUserDto.getUsername())
                .role(createUserDto.getRole())
                // .password(createUserDto.getPassword())
                .build();
        return userEntity;
    }

    public UserEntity userEntity(UserEntity existingUser, UpdateUserDto updateUserDto) {
        if (updateUserDto.getUsername() != null) {
            existingUser.setUsername(updateUserDto.getUsername());
        }
        // if (updateUserDto.getPassword() != null) {
        // userEntityBuilder.password(updateUserDto.getPassword());
        // }
        if (updateUserDto.getRole() != null) {
            existingUser.setRole(updateUserDto.getRole());
        }

        return existingUser;
    }
}
