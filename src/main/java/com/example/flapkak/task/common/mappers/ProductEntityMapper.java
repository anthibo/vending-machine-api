package com.example.flapkak.task.common.mappers;

import com.example.flapkak.task.dtos.CreateProductDto;
import com.example.flapkak.task.dtos.CreateUserDto;
import com.example.flapkak.task.dtos.UpdateProductDto;
import com.example.flapkak.task.entity.ProductEntity;
import com.example.flapkak.task.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component

public class ProductEntityMapper {
    public ProductEntity productEntity(CreateProductDto createProductDto, UserEntity seller) {
        ProductEntity productEntity = ProductEntity.builder()
                .cost(createProductDto.getCost())
                .name(createProductDto.getProductName())
                .quantity(createProductDto.getQuantity())
                .seller(seller)
                .build();
        return productEntity;
    }

    public ProductEntity productEntity(UpdateProductDto updateProductDto, UserEntity seller) {
        ProductEntity.ProductEntityBuilder productBuilder = ProductEntity.builder();

        if (updateProductDto.getProductName() != null) {
            productBuilder.name(updateProductDto.getProductName());
        }

        if (updateProductDto.getCost() > 0) {
            productBuilder.cost(updateProductDto.getCost());
        }

        productBuilder.seller(seller);

        return productBuilder.build();
    }
}
