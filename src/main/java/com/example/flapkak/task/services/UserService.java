package com.example.flapkak.task.services;

import com.example.flapkak.task.common.exceptions.BadRequestException;
import com.example.flapkak.task.common.exceptions.InternalServerException;
import com.example.flapkak.task.common.exceptions.NotFoundException;
import com.example.flapkak.task.common.mappers.UserEntityMapper;
import com.example.flapkak.task.dtos.AddDepositDto;
import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.dtos.UpdateUserDto;
import com.example.flapkak.task.entity.UserEntity;
import com.example.flapkak.task.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Slf4j
@Validated
public class UserService {
    private final UserRepository userRepository;
    // private final BCryptPasswordEncoder passwordEncoder;
    private final UserEntityMapper userEntityMapper;

    @Autowired
    public UserService(
            UserRepository userRepository,
            // BCryptPasswordEncoder passwordEncoder,
            UserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        // this.passwordEncoder = passwordEncoder;
        this.userEntityMapper = userEntityMapper;
    }

    public UserEntity createUser(CreateUserDto createUserDto) throws BadRequestException {

        log.info("Creating new user of username: {}",
                createUserDto.getUsername());
        // String hashedPassword = passwordEncoder.encode(createUserDto.getPassword());
        // createUserDto.setPassword(hashedPassword);
        UserEntity existingUser = userRepository.findByUsername(createUserDto.getUsername()).get();
        if (existingUser != null) {
            log.error("User with username: {} already exists", createUserDto.getUsername());
            // throw exception user already exists
            throw new BadRequestException("user already exists");
        }
        UserEntity userEntity = userEntityMapper.userEntity(createUserDto);
        return userRepository.save(userEntity);

    }

    public UserEntity getUser(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        log.info("user: {}", user.toString());
        return user;
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity deposit(Long userId, AddDepositDto addDepositDto) throws NotFoundException {
        log.info("Depositing with user: {}", userId);
        try {
            UserEntity existingUser = userRepository.findById(userId).orElse(null);
            if (existingUser == null) {
                log.error("User with id: {} not found", userId);
                throw new NotFoundException("User not found");
            }

            double newDeposit = existingUser.getDeposit() + addDepositDto.getCoin();
            existingUser.setDeposit(newDeposit);
            UserEntity updatedUser = userRepository.save(existingUser);
            log.info("Deposit success with user: {}. Current deposit should be: {}, actual: {}",
                    userId,
                    newDeposit,
                    updatedUser.getDeposit());

            return updatedUser;
        } catch (Exception e) {
            log.error("Error depositing user: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
    }

    public UserEntity updateUser(Long userId, UpdateUserDto updateUserDto) {
        try {
            UserEntity existingUser = userRepository.findById(userId).orElse(null);

            if (existingUser == null) {
                log.error("User with id: {} not found", userId);
                throw new NotFoundException("User not found");
            }

            // if (updateUserDto.getPassword() != null) {
            // String hashedPassword = passwordEncoder.encode(updateUserDto.getPassword());
            // updateUserDto.setPassword(hashedPassword);
            // }

            UserEntity mappedUpdateUserEntity = userEntityMapper.userEntity(existingUser, updateUserDto);
            UserEntity updatedUser = userRepository.save(mappedUpdateUserEntity);

            return userRepository.save(updatedUser);
        } catch (Exception e) {
            log.error("Error updating user: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
    }

    public UserEntity updateUser(UserEntity updatedUserEntity) {
        try {
            UserEntity updatedUser = userRepository.save(updatedUserEntity);
            return updatedUser;
        } catch (Exception e) {
            log.error("Error updating user: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
    }

    public void deleteUser(Long id) throws BadRequestException, InternalServerException {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting user: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
    }

    public UserEntity reset(Long userId) {
        try {
            UserEntity existingUser = userRepository.findById(userId).orElse(null);
            if (existingUser == null) {
                log.error("User with id: {} not found", userId);
                throw new NotFoundException("User not found");
            }

            existingUser.setDeposit(0);
            UserEntity updatedUser = userRepository.save(existingUser);
            return updatedUser;
        } catch (Exception e) {
            log.error("Error resetting user: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
    }
}