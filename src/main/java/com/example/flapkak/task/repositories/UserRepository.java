package com.example.flapkak.task.repositories;

import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.entity.UserEntity;
import com.example.flapkak.task.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
//    UserEntity createUser(CreateUserDto createUserDto);

    BigDecimal incrementUserDeposit(Long userId, BigDecimal deposit);

}