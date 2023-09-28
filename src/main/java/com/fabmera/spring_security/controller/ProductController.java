package com.fabmera.spring_security.controller;

import com.fabmera.spring_security.dto.ProductRecordDTO;
import com.fabmera.spring_security.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductRecordDTO>> findAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProductRecordDTO> createOne(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productRecordDTO));
    }
}
