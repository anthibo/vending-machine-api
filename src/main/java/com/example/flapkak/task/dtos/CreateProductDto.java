package com.example.flapkak.task.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateProductDto {
    @NotBlank(message = "quantity is required")
    private int quantity;

    @NotBlank(message = "productName is required")
    private String productName;

    @NotBlank(message = "cost is required")
    private double cost;

    @Override
    public String toString() {
        return "CreateProductDto{" +
                "quantity=" + quantity +
                ", productName='" + productName + '\'' +
                ", cost=" + cost +
                '}';
    }
}