package com.fabmera.spring_security.service;

import com.fabmera.spring_security.dto.ProductRecordDTO;

import java.util.List;

public interface ProductService {

    List<ProductRecordDTO> findAll();
    ProductRecordDTO create(ProductRecordDTO productRecordDTO);
}
