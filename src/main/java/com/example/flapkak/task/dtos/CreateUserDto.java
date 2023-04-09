package com.example.flapkak.task.dtos;

import com.example.flapkak.task.enums.UserRoles;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CreateUserDto {
    @NotBlank(message = "Name is required")
    private  String username;

    @NotBlank(message = "Name is required")
    private String password;

    @NotBlank(message = "User role is required")
    @Enumerated(EnumType.STRING)
    private UserRoles role;
}
