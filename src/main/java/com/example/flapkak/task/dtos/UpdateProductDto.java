package com.example.flapkak.task.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import io.micrometer.common.lang.Nullable;

@Getter
@Setter
@ToString
public class UpdateProductDto {
    @Nullable
    private String productName;

    @Nullable
    private int cost;
}