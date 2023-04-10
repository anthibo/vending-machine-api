package com.example.flapkak.task.dtos;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyProductDto {
    @NotNull
    private int amount;
}
