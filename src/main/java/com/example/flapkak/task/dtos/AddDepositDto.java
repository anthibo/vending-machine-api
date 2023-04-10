package com.example.flapkak.task.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.flapkak.task.common.validation.ValidCoinValue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddDepositDto {
    @ValidCoinValue
    @NotNull
    @NotBlank
    private int coin;
}
