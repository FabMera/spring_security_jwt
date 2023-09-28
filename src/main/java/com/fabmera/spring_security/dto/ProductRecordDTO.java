package com.fabmera.spring_security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRecordDTO(@NotBlank String name, @NotNull BigDecimal price) {
}
