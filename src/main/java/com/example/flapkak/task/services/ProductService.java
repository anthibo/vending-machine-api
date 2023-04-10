package com.example.flapkak.task.services;

import com.example.flapkak.task.common.enums.UserRoles;
import com.example.flapkak.task.common.exceptions.InternalServerException;
import com.example.flapkak.task.common.exceptions.NotFoundException;
import com.example.flapkak.task.common.exceptions.UnauthorizedException;
import com.example.flapkak.task.common.mappers.ProductEntityMapper;
import com.example.flapkak.task.common.valueObjects.ProductsResponse;
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

@Service
@Slf4j
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
        try {
            log.info("Creating new product of sellerID: {}",
                    sellerId);
            UserEntity user = userService.getUser(sellerId);
            if (user == null || user.getRole() != UserRoles.SELLER) {
                log.error("Seller with id: {} not found", sellerId);
                throw new UnauthorizedException("you are not authorized to create products");
            }
            ProductEntity productEntity = productEntityMapper.productEntity(createProductDto, user);
            return productRepository.save(productEntity);
        } catch (Exception e) {
            log.error("Error creating product: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();

        }
    }

    public ProductEntity findProductById(long productId) {
        try {
            Optional<ProductEntity> productEntity = productRepository.findById(productId);
            if (productEntity == null) {
                log.error("product with id: {} not found", productId);
                throw new NotFoundException("product not found");
            }
            return productEntity.get();
        } catch (Exception e) {
            log.error("Error findProductById: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
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

    public ProductEntity updateProduct(UpdateProductDto updateProductDto, long sellerId, long productId) {
        try {
            log.info("update product of id: {}",
                    productId);
            ProductEntity existingProduct = findProductById(productId);
            UserEntity seller = userService.getUser(sellerId);
            if (seller == null || existingProduct.getSeller().getId() != sellerId) {
                throw new UnauthorizedException("you are not authorized to update this product");
            }
            ProductEntity productEntity = productEntityMapper.productEntity(updateProductDto, seller);
            return productRepository.save(productEntity);
        } catch (Exception e) {
            log.error("Error updateProduct: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
    }

    public void deleteProduct(long sellerId, long productId) {
        try {
            log.info("delete product of id: {}",
                    productId);
            ProductEntity existingProduct = findProductById(productId);
            UserEntity seller = userService.getUser(sellerId);
            if (seller == null || existingProduct.getSeller().getId() != sellerId) {
                throw new UnauthorizedException("you are not authorized to delete this product");
            }
            productRepository.delete(existingProduct);
        } catch (Exception e) {
            log.error("Error deleteProduct: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
    }

    public ProductEntity buyProduct(long buyerId, long productId) {
        try {
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
            if (existingProduct.getQuantity() == 0) {
                log.error("Product with id: {} is not available", productId);
                throw new NotFoundException("product not available now");
            }
            // check if buyer has enough money
            if (user.getDeposit() < existingProduct.getCost()) {
                log.error("Buyer with id: {} does not have enough money", buyerId);
                throw new NotFoundException("You do not have enough coins!");
            }
            existingProduct.setQuantity(existingProduct.getQuantity() - 1);
            user.setDeposit(user.getDeposit() - existingProduct.getCost());
            userService.updateUser(user);
            return productRepository.save(existingProduct);
        } catch (Exception e) {
            log.error("Error buyProduct: {}, stackTrace: {}",
                    e.getMessage(),
                    e.getStackTrace());
            throw new InternalServerException();
        }
    }
}