package com.example.flapkak.task.dtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

import com.example.flapkak.task.common.enums.UserRoles;

@Getter
@Setter
@ToString
public class CreateUserDto {
    @NotBlank(message = "Name is required")
    private  String username;

    // @NotBlank(message = "Password is required")
    // private String password;

    @NotBlank(message = "User role is required")
    @Enumerated(EnumType.STRING)
    private UserRoles role;
}
