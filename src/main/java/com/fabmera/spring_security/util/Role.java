package com.fabmera.spring_security.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public enum Role {
    CUSTOMER(List.of(Permission.READ_ALL_PRODUCTS)),
    ADMINSTRATOR(List.of(Permission.READ_ALL_PRODUCTS, Permission.SAVE_ONE_PRODUCT));

    @Setter
    @Getter
    private List<Permission> permissionList;

}
