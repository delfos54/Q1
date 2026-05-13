package com.example.coffee.connect.service;

import org.springframework.stereotype.Service;
import com.example.coffee.connect.DTO.productosDTO;
import com.example.coffee.connect.model.productos;
import com.example.coffee.connect.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductosService {

    private final ProductoRepository repo;

    public productos crear(productosDTO dto) {
        log.info("crear producto", keyValue("nombre", dto.getNombre()));
        
        productos a = new productos();
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setPrecio(dto.getPrecio());
        a.setStock(dto.getStock());
        a.setCategoria(dto.getCategoria());
        
        return repo.save(a);
    } 

    //lista para ver los productos
    public List<productos> listar() {
        log.info("listar productos");
        return repo.findAll();
    }

    //optener el cafe por id
    public productos obtener (Integer id) {
        log.info("obtener producto", keyValue("id", id));
        return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }
    //actualizar producto 
    public productos actualizar(Integer id , productosDTO dto) {
        log.info("actualizar producto", keyValue("id", id));
        productos a = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
        
        a.setNombre(dto.getNombre());
        a.setDescripcion(dto.getDescripcion());
        a.setPrecio(dto.getPrecio());
        a.setStock(dto.getStock());
        a.setCategoria(dto.getCategoria());
        return repo.save(a);
    }
    //eliminar producto
    public void eliminar (Integer id){
        log.warn("eliminar producto ",keyValue ("id", id));
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: No existe");
        }
        repo.deleteById(id);
    }



}