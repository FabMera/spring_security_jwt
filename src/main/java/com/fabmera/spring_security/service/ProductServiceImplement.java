package com.fabmera.spring_security.service;

import com.fabmera.spring_security.dto.ProductRecordDTO;
import com.fabmera.spring_security.entity.ProductModel;
import com.fabmera.spring_security.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImplement implements ProductService {

    private ProductRepository productRepository;

    @Override
    public List<ProductRecordDTO> findAll() {
        List<ProductModel> productEntity = productRepository.findAll();
        if (!productEntity.isEmpty()) {
            return productEntity.stream().map(productModel -> new ProductRecordDTO(productModel.getName(), productModel.getPrice())).toList();
        } else {
            return Collections.emptyList();
        }

    }

    @Override
    public ProductRecordDTO create(ProductRecordDTO productRecordDTO) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDTO, productModel);
        productModel = productRepository.save(productModel);
        return new ProductRecordDTO(productModel.getName(), productModel.getPrice());
    }
}
