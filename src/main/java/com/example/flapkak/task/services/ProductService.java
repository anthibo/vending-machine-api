package com.example.flapkak.task.services;

import com.example.flapkak.task.dtos.CreateProductDto;
import com.example.flapkak.task.dtos.UpdateProductDto;
import com.example.flapkak.task.entity.ProductEntity;
import com.example.flapkak.task.entity.UserEntity;
import com.example.flapkak.task.mappers.ProductEntityMapper;
import com.example.flapkak.task.repositories.ProductRepository;
import com.example.flapkak.task.valueObjects.ProductsResponse;

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
            UserEntity seller = userService.getUser(sellerId);
            if (seller == null) {
                log.error("Seller with id: {} not found", sellerId);
                // throw exception user not found

                return null;
            }
            ProductEntity productEntity = productEntityMapper.productEntity(createProductDto, seller);
            return productRepository.save(productEntity);
        } catch (Exception e) {
            log.error("Error while creating new product, error: {}", e.getMessage());

            return null;
        }
    }

    public ProductEntity findProductById(long productId) {
        try {
            Optional<ProductEntity> productEntity = productRepository.findById(productId);
            if (productEntity == null) {
                // return a message that not found; (throw exception)
                return null;
            }
            return productEntity.get();
        } catch (Exception e) {
            log.error("Error while creating new product, error: {}", e.getMessage());

            return null;
        }
    }

    public ProductsResponse findAllProducts() {
        try {
            List<ProductEntity> products = productRepository.findAll();
            if (products == null) {
                // return a message that not found; (throw exception)
                return null;
            }
            return new ProductsResponse(products);
        } catch (Exception e) {
            log.error("Error while fetching all products {}", e.getMessage());
            // internal server error
            return null;
        }
    }

    public ProductEntity updateProduct(UpdateProductDto updateProductDto, long sellerId, long productId) {
        try {
            log.info("update product of id: {}",
                    productId);
            ProductEntity existingProduct = findProductById(productId);
            UserEntity seller = userService.getUser(sellerId);
            if (seller == null || existingProduct.getSeller().getId() != sellerId) {
                log.error("Seller with id: {} not found", sellerId);
                // throw exception 404 not found product

                return null;
            }
            ProductEntity productEntity = productEntityMapper.productEntity(updateProductDto, seller);
            return productRepository.save(productEntity);
        } catch (Exception e) {
            log.error("Error while creating new product, error: {}", e.getMessage());

            return null;
        }
    }

    public void deleteProduct(long sellerId, long productId) {
        try {
            log.info("delete product of id: {}",
                    productId);
            ProductEntity existingProduct = findProductById(productId);
            UserEntity seller = userService.getUser(sellerId);
            if (seller == null || existingProduct.getSeller().getId() != sellerId) {
                log.error("Seller with id: {} not found", sellerId);
                // throw exception 404 not found product

                return;
            }
            productRepository.delete(existingProduct);
        } catch (Exception e) {
            log.error("Error while deleting product :{}, error: {}", productId, e.getMessage());

            return;
        }
    }

    public ProductEntity buyProduct(long buyerId, long productId) {
        try {
            log.info("buy product of id: {}",
                    productId);
            ProductEntity existingProduct = findProductById(productId);
            UserEntity buyer = userService.getUser(buyerId);
            if (existingProduct == null) {
                log.error("product with id: {} not found", productId);
                // throw exception 404 not found product

                return null;
            }
            // check if product is available
            if (existingProduct.getQuantity() == 0) {
                log.error("Product with id: {} is not available", productId);
                // throw exception 400 not available product
                return null;
            }
            // check if buyer has enough money
            if (buyer.getDeposit() < existingProduct.getCost()) {
                log.error("Buyer with id: {} has not enough money", buyerId);
                // throw exception 400 not enough deposit

                return null;
            }
            existingProduct.setQuantity(existingProduct.getQuantity() - 1);
            buyer.setDeposit(buyer.getDeposit() - existingProduct.getCost());
            userService.updateUser(buyer);
            return productRepository.save(existingProduct);
        } catch (Exception e) {
            log.error("Error while buying product :{}, error: {}", productId, e.getMessage());
            // throw exception 500
            return null;
        }
    }
}