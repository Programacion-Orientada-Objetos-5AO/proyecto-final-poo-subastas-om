package ar.edu.huergo.fastbid.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.fastbid.dto.ProductoDTO;
import ar.edu.huergo.fastbid.entity.Producto;

@Component
public class ProductoMapper {

    // Entity → DTO
    public ProductoDTO toDto(Producto producto) {
        if (producto == null) {
            return null;
        }

        return new ProductoDTO(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecioInicial(),
                producto.getImagenes(),
                producto.getCategoriaId(),
                producto.getEstado(),
                producto.getFechaPublicacion(),
                producto.getFechaFin(),
                producto.getUsuarioId(),
                producto.getSubastaId(),
                producto.getPrecioCompraInmediata(),
                producto.getCondicion(),
                producto.getUbicacion(),
                producto.getCantidad()
        );
    }

    public Producto toEntity(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setIdProducto(dto.idProducto());
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setPrecioInicial(dto.precioInicial());
        producto.setImagenes(dto.imagenes());
        producto.setCategoriaId(dto.categoriaId());
        producto.setEstado(dto.estado());
        producto.setFechaPublicacion(dto.fechaPublicacion());
        producto.setFechaFin(dto.fechaFin());
        producto.setUsuarioId(dto.usuarioId());
        producto.setSubastaId(dto.subastaId());
        producto.setPrecioCompraInmediata(dto.precioCompraInmediata());
        producto.setCondicion(dto.condicion());
        producto.setUbicacion(dto.ubicacion());
        producto.setCantidad(dto.cantidad());

        return producto;
    }

    public List<ProductoDTO> toDtoList(List<Producto> productos) {
        if (productos == null) {
            return new ArrayList<>();
        }
        return productos.stream().map(this::toDto).collect(Collectors.toList());
    }

}
