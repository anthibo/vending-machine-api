package com.example.flapkak.task.dtos;

import com.example.flapkak.task.common.enums.UserRoles;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class UpdateUserDto {
    @Nullable()
    private  String username;

    // @Nullable()
    // private String password;

    @Nullable()
    @Enumerated(EnumType.STRING)
    private UserRoles role;
}
