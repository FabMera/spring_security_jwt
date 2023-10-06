package com.fabmera.spring_security.controller;

import com.fabmera.spring_security.dto.ProductRecordDTO;
import com.fabmera.spring_security.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @GetMapping
    public ResponseEntity<List<ProductRecordDTO>> findAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PreAuthorize("hasAuthority('SAVE_ONE_PRODUCT')")
    @PostMapping
    public ResponseEntity<ProductRecordDTO> createOne(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(productRecordDTO));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e, HttpServletRequest request) {
        Map<String, String> apiError = new HashMap<>();
        apiError.put("message", e.getLocalizedMessage());
        apiError.put("timestamp", new Date().toString());
        apiError.put("url", request.getRequestURL().toString());
        apiError.put("http-method", request.getMethod());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof AccessDeniedHandler) {
            status = HttpStatus.FORBIDDEN;
        }
        return ResponseEntity.status(status).body(apiError);
    }
}
