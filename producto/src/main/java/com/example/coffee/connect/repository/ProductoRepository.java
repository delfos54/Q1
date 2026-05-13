package com.example.coffee.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.coffee.connect.model.productos;

@Repository
public interface ProductoRepository extends JpaRepository<productos, Integer>{

}
    



