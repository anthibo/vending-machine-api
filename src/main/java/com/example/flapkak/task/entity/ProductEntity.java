package com.example.flapkak.task.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Entity
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double cost;

    @Column(nullable = false)
    private Integer quantity;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private UserEntity seller;
}