package com.example.flapkak.task.controllers;

import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.entity.UserEntity;
import com.example.flapkak.task.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(CreateUserDto createUserDto){
        UserEntity userEntity = userService.createUser(createUserDto);
        return ResponseEntity.ok(userEntity);
    }
}
