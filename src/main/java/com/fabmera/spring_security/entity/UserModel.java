package com.fabmera.spring_security.entity;

import com.fabmera.spring_security.util.Role;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "user_model")
public class UserModel extends RepresentationModel<UserModel> implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String username;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;

}
