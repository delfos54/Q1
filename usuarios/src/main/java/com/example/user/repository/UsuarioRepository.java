package com.example.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.model.user;

public interface  UsuarioRepository extends JpaRepository<user, Long> {
    Optional<user> findByUsername(String username);

}
