package com.example.flapkak.task.services;

import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.entity.UserEntity;
import com.example.flapkak.task.mappers.UserEntityMapper;
import com.example.flapkak.task.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private UserEntityMapper userEntityMapper;

    @Autowired
    public UserService(UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userEntityMapper = userEntityMapper;
    }

    public UserEntity createUser(CreateUserDto createUserDto) {
        log.info("Creating new user of username: {}",
                createUserDto.getUsername());
        String hashedPassword = passwordEncoder.encode(createUserDto.getPassword());
        createUserDto.setPassword(hashedPassword);
        UserEntity userEntity = userEntityMapper.userEntity(createUserDto);
        return userRepository.save(userEntity);
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // public List<UserEntity> getAllUsersByRole(String role) {
    //// return userRepository.findAllByRole(role);
    // }

//    public UserEntity updateUser(user) {
//        return userRepository.save(user);
//    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}