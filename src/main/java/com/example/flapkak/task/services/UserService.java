package com.example.flapkak.task.services;

import com.example.flapkak.task.dtos.AddDepositDto;
import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.dtos.UpdateUserDto;
import com.example.flapkak.task.entity.UserEntity;
import com.example.flapkak.task.mappers.UserEntityMapper;
import com.example.flapkak.task.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserEntityMapper userEntityMapper;

    @Autowired
    public UserService(
            UserRepository userRepository,
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

    public UserEntity deposit(Long userId, AddDepositDto addDepositDto) {

        try {
            UserEntity existingUser = userRepository.findById(userId).orElse(null);
            if (existingUser == null) {
                log.error("User with id: {} not found", userId);
                // throw exception user not found

                return null;
            }

            existingUser.setDeposit(existingUser.getDeposit() + addDepositDto.getCoin());
            UserEntity updatedUser = userRepository.save(existingUser);

            return updatedUser;
        } catch (Exception e) {
            log.error("Error depositing {} to user with id: {}", addDepositDto.getCoin(), userId);
            return null;
        }
    }

    public UserEntity updateUser(Long userId, UpdateUserDto updateUserDto) {
        try {
            UserEntity existingUser = userRepository.findById(userId).orElse(null);

            if (existingUser == null) {
                log.error("User with id: {} not found", userId);
                // throw exception user not found

                return null;
            }

            if (updateUserDto.getPassword() != null) {
                String hashedPassword = passwordEncoder.encode(updateUserDto.getPassword());
                updateUserDto.setPassword(hashedPassword);
            }

            UserEntity mappedUpdateUserEntity = userEntityMapper.userEntity(userId, updateUserDto);
            UserEntity updatedUser = userRepository.save(mappedUpdateUserEntity);

            return userRepository.save(updatedUser);
        } catch (Exception e) {
            log.error("Error updating user with id: {}", userId);
            return null;
        }
    }

    public UserEntity updateUser(UserEntity updatedUserEntity) {
        try {
            UserEntity updatedUser = userRepository.save(updatedUserEntity);
            return updatedUser;

        } catch (Exception e) {
            log.error("Error updating user with id: {}", updatedUserEntity.getId());
            return null;
        }
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting user with id: {}", id);
        }
    }

    public UserEntity reset(Long userId) {
        try {
            UserEntity existingUser = userRepository.findById(userId).orElse(null);
            if (existingUser == null) {
                log.error("User with id: {} not found", userId);
                // throw exception user not found
                return null;
            }

            existingUser.setDeposit(0);
            UserEntity updatedUser = userRepository.save(existingUser);
            return updatedUser;
        } catch (Exception e) {
            log.error("Error resetting user with id: {}", userId);
            // throw exception user not found
            return null;
        }
    }
}