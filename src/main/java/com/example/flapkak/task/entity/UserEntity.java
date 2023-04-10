package com.example.flapkak.task.entity;


import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import com.example.flapkak.task.common.enums.UserRoles;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    // @NotBlank
    // @Column(nullable = false)
    // private String password;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoles role;

    @DecimalMin(value = "0.00")
    @Column(nullable = false, columnDefinition="NUMERIC(10, 2) DEFAULT '0.00'")
    private double deposit;
}
