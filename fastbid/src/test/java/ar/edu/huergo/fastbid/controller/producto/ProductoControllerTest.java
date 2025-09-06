package ar.edu.huergo.fastbid.controller.producto;

import ar.edu.huergo.fastbid.dto.ProductoDTO;
import ar.edu.huergo.fastbid.entity.Producto;
import ar.edu.huergo.fastbid.mapper.ProductoMapper;
import ar.edu.huergo.fastbid.service.productoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(productoController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductoControllerTest {

    @TestConfiguration
    static class Beans {
        @Bean ProductoMapper productoMapper() { return new ProductoMapper(); }
        @Bean productoService productoService() { return Mockito.mock(productoService.class); }
    }

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;
    @Autowired productoService productoService; // mock registrado arriba

    private ProductoDTO dtoBase() {
        return new ProductoDTO(
                1L,"Item","Desc",100.0,List.of("img"),10L,"ACTIVO",
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(7),
                5L,7L,120.0,"NUEVO","CABA",2
        );
    }
    private Producto toEntity(ProductoDTO d) {
        Producto p = new Producto();
        p.setIdProducto(d.idProducto());
        p.setNombre(d.nombre());
        p.setDescripcion(d.descripcion());
        p.setPrecioInicial(d.precioInicial());
        p.setImagenes(d.imagenes());
        p.setCategoriaId(d.categoriaId());
        p.setEstado(d.estado());
        p.setFechaPublicacion(d.fechaPublicacion());
        p.setFechaFin(d.fechaFin());
        p.setUsuarioId(d.usuarioId());
        p.setSubastaId(d.subastaId());
        p.setPrecioCompraInmediata(d.precioCompraInmediata());
        p.setCondicion(d.condicion());
        p.setUbicacion(d.ubicacion());
        p.setCantidad(d.cantidad());
        return p;
    }

    @Test
    void listar_ok() throws Exception {
        var e = toEntity(dtoBase());
        when(productoService.obtenerProductos()).thenReturn(List.of(e));

        mvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Item"))
                .andExpect(jsonPath("$[0].estado").value("ACTIVO"));
    }

    @Test
    void obtenerPorId_ok() throws Exception {
        var e = toEntity(dtoBase());
        when(productoService.obteneProductoPorId(1L)).thenReturn(e); // tu método tiene ese nombre

        mvc.perform(get("/productos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.precioInicial").value(100.0));
    }

    @Test
    void crear_ok201_conLocation() throws Exception {
        var in = new ProductoDTO(
                null,"Item","Desc",100.0,List.of("img"),10L,"ACTIVO",
                LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(7),
                5L,7L,120.0,"NUEVO","CABA",2
        );
        var out = dtoBase();
        when(productoService.crearProducto(any(Producto.class))).thenReturn(toEntity(out));

        mvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(in)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", Matchers.containsString("/productos/1")))
                .andExpect(jsonPath("$.idProducto").value(1));
    }

    @Test
    void actualizar_ok200() throws Exception {
        var in = dtoBase();
        var actualizado = new ProductoDTO(
                1L,"Item PRO",in.descripcion(),in.precioInicial(),in.imagenes(),
                in.categoriaId(),in.estado(),in.fechaPublicacion(),in.fechaFin(),
                in.usuarioId(),in.subastaId(),in.precioCompraInmediata(),in.condicion(),
                in.ubicacion(),in.cantidad()
        );
        when(productoService.actualizarProducto(eq(1L), any(Producto.class)))
                .thenReturn(toEntity(actualizado));

        mvc.perform(put("/productos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Item PRO"));
    }

    @Test
    void eliminar_noContent204() throws Exception {
        mvc.perform(delete("/productos/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
