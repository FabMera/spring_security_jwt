package com.fabmera.spring_security.repository;

import com.fabmera.spring_security.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
   Optional<UserModel> findByUsername(String username);
}
