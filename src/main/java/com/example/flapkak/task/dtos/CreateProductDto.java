package com.example.flapkak.task.dtos;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CreateProductDto {
    @NotBlank(message = "amountAvailable is required")
    private  int quantity;

    @NotBlank(message = "productName is required")
    private String productName;

    @NotBlank(message = "cost is required")
    private double cost;
}