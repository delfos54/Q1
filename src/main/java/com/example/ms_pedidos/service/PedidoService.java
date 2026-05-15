package com.example.ms_pedidos.service;

import com.example.ms_pedidos.dto.PedidoRequestDTO;
import com.example.ms_pedidos.exception.GlobalExceptionHandler;
import com.example.ms_pedidos.model.DetallePedido;
import com.example.ms_pedidos.model.Pedido;
import com.example.ms_pedidos.repository.DetallePedidoRepository;
import com.example.ms_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    // LISTAR PEDIDOS
    public List<Pedido> listarPedidos() {

        log.info("Listando pedidos");

        return pedidoRepository.findAll();
    }

    // OBTENER PEDIDO POR ID
    public Pedido obtenerPedido(Long id) {

        log.info("Buscando pedido con id {}", id);

        return pedidoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido no encontrado"));
    }

    // CREAR PEDIDO
    public Pedido crearPedido(PedidoRequestDTO dto) {

        log.info("Creando pedido");

        Pedido pedido = new Pedido();

        pedido.setUsuarioId(dto.getUsuarioId());
        pedido.setEstado("PENDIENTE");

        double total = 0;

        // CALCULAR TOTAL
        for (PedidoRequestDTO.DetalleDTO detalleDTO : dto.getDetalles()) {

            total += detalleDTO.getCantidad() * detalleDTO.getPrecio();
        }

        pedido.setTotal(total);

        // GUARDAR PEDIDO
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // GUARDAR DETALLES
        for (PedidoRequestDTO.DetalleDTO detalleDTO : dto.getDetalles()) {

            DetallePedido detalle = new DetallePedido();

            detalle.setPedidoId(pedidoGuardado.getId());
            detalle.setProductoId(detalleDTO.getProductoId());
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecio(detalleDTO.getPrecio());

            detallePedidoRepository.save(detalle);
        }

        log.info("Pedido creado correctamente con id {}", pedidoGuardado.getId());

        return pedidoGuardado;
    }

    // ACTUALIZAR ESTADO
    public Pedido actualizarEstado(Long id, String estado) {

        Pedido pedido = obtenerPedido(id);

        pedido.setEstado(estado);

        log.info("Actualizando estado del pedido {}", id);

        return pedidoRepository.save(pedido);
    }

    // ELIMINAR PEDIDO
    public void eliminarPedido(Long id) {

        Pedido pedido = obtenerPedido(id);

        pedidoRepository.delete(pedido);

        log.info("Pedido eliminado con id {}", id);
    }