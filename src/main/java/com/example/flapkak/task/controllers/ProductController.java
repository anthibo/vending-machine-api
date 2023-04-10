package com.example.flapkak.task.controllers;

import com.example.flapkak.task.common.valueObjects.MessageResponse;
import com.example.flapkak.task.common.valueObjects.ProductsResponse;
import com.example.flapkak.task.dtos.CreateProductDto;
import com.example.flapkak.task.dtos.UpdateProductDto;
import com.example.flapkak.task.entity.ProductEntity;
import com.example.flapkak.task.services.ProductService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/api/products", produces = "application/vnd.api.v1+json")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // get products
    @GetMapping
    public ResponseEntity<ProductsResponse> listProducts() {
        ProductsResponse productsResponse = productService.findAllProducts();
        return ResponseEntity.ok(productsResponse);
    }

    // Create a product
    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestHeader("sellerId") long sellerId,CreateProductDto createUserDto) {
        log.info("create new user with data: {}", createUserDto.toString());
        ProductEntity productEntity = productService.createProduct(createUserDto, sellerId);
        return ResponseEntity.ok(productEntity);
    }

    // Update Product
    @PutMapping("/{productId}")
    public ResponseEntity<ProductEntity> createUser(@RequestBody UpdateProductDto updateProductDto,
            @PathVariable long productId) {
        log.info("create new user with data: {}", updateProductDto.toString());
        long sellerId = 1;
        ProductEntity productEntity = productService.updateProduct(updateProductDto, sellerId, productId);
        return ResponseEntity.ok(productEntity);
    }

    // Delete Product
    @DeleteMapping("/{productId}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable long productId) {
        log.info("delete user of id: {}", productId);
        long sellerId = 1;
        productService.deleteProduct(sellerId, productId);
        return ResponseEntity.ok(
                new MessageResponse("Product deleted successfully!"));
    }

    // @PostMapping("/{productId}/buy")
    // public ResponseEntity<MessageResponse> buyProduct(@PathVariable long productId, @RequestHeader("buyerId") long buyerId, @RequestBody ) {
    //     productService.buyProduct(buyerId, productId);
    //     return ResponseEntity.ok(
    //             new MessageResponse("Product deleted successfully!"));
    // }
}
