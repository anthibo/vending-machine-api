package com.example.flapkak.task.services;

import com.example.flapkak.task.common.enums.UserRoles;
import com.example.flapkak.task.common.exceptions.InternalServerException;
import com.example.flapkak.task.common.exceptions.NotFoundException;
import com.example.flapkak.task.common.exceptions.UnauthorizedException;
import com.example.flapkak.task.common.mappers.ProductEntityMapper;
import com.example.flapkak.task.common.valueObjects.ProductsResponse;
import com.example.flapkak.task.dtos.BuyProductDto;
import com.example.flapkak.task.dtos.CreateProductDto;
import com.example.flapkak.task.dtos.UpdateProductDto;
import com.example.flapkak.task.entity.ProductEntity;
import com.example.flapkak.task.entity.UserEntity;
import com.example.flapkak.task.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@Validated
public class ProductService {
    private final UserService userService;
    private final ProductRepository productRepository;
    private final ProductEntityMapper productEntityMapper;

    @Autowired
    public ProductService(
            UserService userService,
            ProductRepository productRepository,
            ProductEntityMapper productEntityMapper) {
        this.userService = userService;
        this.productRepository = productRepository;
        this.productEntityMapper = productEntityMapper;
    }

    public ProductEntity createProduct(CreateProductDto createProductDto, long sellerId) {
        log.info("Creating new product for sellerID: {}",
                sellerId);
        UserEntity user = userService.getUser(sellerId);
        if (user == null || user.getRole() != UserRoles.SELLER) {
            log.error("Seller with id: {} not found", sellerId);
            throw new UnauthorizedException("you are not authorized to create products");
        }
        log.info("createProductDto data: {}", createProductDto.toString());
        ProductEntity productEntity = productEntityMapper.productEntity(createProductDto, user);
        log.info("product entity mapped: {}", productEntity);
        return productRepository.save(productEntity);
    }

    public ProductEntity findProductById(long productId) {
        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        if (!productEntity.isPresent()) {
            log.error("product with id: {} not found", productId);
            throw new NotFoundException("product not found");
        }
        return productEntity.get();
    }

    public ProductsResponse findAllProducts() {
        try {
            List<ProductEntity> products = productRepository.findAll();
            return new ProductsResponse(products);
        } catch (Exception e) {
            log.error("Error findAllProducts: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
    }

    public ProductEntity updateProduct(UpdateProductDto updateProductDto, long sellerId, long productId)
            throws UnauthorizedException    , NotFoundException {
        log.info("update product of id: {}",
                productId);
        ProductEntity existingProduct = findProductById(productId);
        UserEntity seller = userService.getUser(sellerId);
        if (seller == null || existingProduct.getSeller().getId() != sellerId) {
            throw new UnauthorizedException("you are not authorized to update this product");
        }
        ProductEntity productEntity = productEntityMapper.productEntity(updateProductDto, seller);
        return productRepository.save(productEntity);
    }

    public void deleteProduct(long sellerId, long productId) {
        log.info("delete product of id: {}",
                productId);
        ProductEntity existingProduct = findProductById(productId);
        UserEntity seller = userService.getUser(sellerId);
        if (seller == null || existingProduct.getSeller().getId() != sellerId) {
            throw new UnauthorizedException("you are not authorized to delete this product");
        }
        productRepository.deleteById(productId);
    }

    public UserEntity buyProduct(long buyerId, long productId, BuyProductDto buyProductDto) {
        log.info("buy product of id: {}",
                productId);
        ProductEntity existingProduct = findProductById(productId);
        UserEntity user = userService.getUser(buyerId);
        if (user == null || user.getRole() != UserRoles.BUYER) {
            throw new UnauthorizedException("you are not authorized to buy");
        }
        if (existingProduct == null) {
            log.error("product with id: {} not found", productId);
            throw new NotFoundException("product not found");
        }
        if (buyProductDto.getAmount() > existingProduct.getQuantity()) {
            log.error("Product with id: {} is not available", productId);
            throw new NotFoundException("product not available now");
        }
        // check if buyer has enough money
        if (user.getDeposit() < existingProduct.getCost()) {
            log.error("Buyer with id: {} does not have enough money", buyerId);
            throw new NotFoundException("You do not have enough coins!");
        }
        existingProduct.setQuantity(existingProduct.getQuantity() - buyProductDto.getAmount());
        user.setDeposit(user.getDeposit() - (existingProduct.getCost() * buyProductDto.getAmount()));
        UserEntity updatedUser = userService.updateUser(user);
        productRepository.save(existingProduct);
        return updatedUser;
    }
}