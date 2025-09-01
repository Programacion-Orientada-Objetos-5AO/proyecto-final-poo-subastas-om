package ar.edu.huergo.fastbid.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProductoDTO(
        Long idProducto,
        String nombre,
        String descripcion,
        double precioInicial,
        List<String> imagenes,
        Long categoriaId,
        String estado,
        LocalDateTime fechaPublicacion,
        LocalDateTime fechaFin,
        Long usuarioId,
        Long subastaId,
        Double precioCompraInmediata,
        String condicion,
        String ubicacion,
        int cantidad
) {}