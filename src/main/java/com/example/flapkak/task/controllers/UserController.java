package com.example.flapkak.task.controllers;

import com.example.flapkak.task.common.valueObjects.MessageResponse;
import com.example.flapkak.task.dtos.AddDepositDto;
import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.dtos.UpdateUserDto;
import com.example.flapkak.task.entity.UserEntity;
import com.example.flapkak.task.services.UserService;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/api/users", produces = "application/vnd.api.v1+json")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // register user
    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        log.info("create new user with data: {}", createUserDto.toString());
        UserEntity userEntity = userService.createUser(createUserDto);
        return ResponseEntity.ok(userEntity);
    }

    // delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long userId) {
        log.info("delete user of id: {}", userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok(
                new MessageResponse("User deleted successfully!"));
    }

    // update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserDto updateUserDto) {
        log.info("update user of id: {} with new data: {}", userId, updateUserDto.toString());
        UserEntity updatedUser = userService.updateUser(userId, updateUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    // deposit user
    @PostMapping("/deposit")
    public ResponseEntity<UserEntity> deposit(@RequestHeader("userId") Long userId, @Valid @RequestBody AddDepositDto addDepositDto) {
        UserEntity updatedUser = userService.deposit(userId, addDepositDto);
        return ResponseEntity.ok(updatedUser);
    }

      // reset user
      @GetMapping("/reset")
      public ResponseEntity<UserEntity> reset(@RequestHeader("userId") Long userId) {
          UserEntity updatedUser = userService.reset(userId);
          return ResponseEntity.ok(updatedUser);
      }
}
