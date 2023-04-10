package com.example.flapkak.task.valueObjects;

import java.util.List;

import com.example.flapkak.task.entity.ProductEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsResponse {
    List<ProductEntity> products;
    public ProductsResponse(List<ProductEntity> products) {
        this.products = products;
    }
}
