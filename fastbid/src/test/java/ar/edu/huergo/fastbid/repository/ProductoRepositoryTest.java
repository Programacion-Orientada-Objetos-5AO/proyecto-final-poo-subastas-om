package ar.edu.huergo.fastbid.repository;

import ar.edu.huergo.fastbid.entity.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductoRepositoryTest {

    @Autowired
    private productoRepository repo;

    private Producto valido() {
        Producto p = new Producto();
        p.setNombre("Mouse Gamer");
        p.setDescripcion("8000 DPI");
        p.setPrecioInicial(5999.90);
        p.setImagenes(List.of("img1"));
        p.setCategoriaId(10L);
        p.setEstado("ACTIVO");
        p.setFechaPublicacion(LocalDateTime.now().minusDays(1));
        p.setFechaFin(LocalDateTime.now().plusDays(7));
        p.setUsuarioId(5L);
        p.setSubastaId(7L);                 // opcional
        p.setPrecioCompraInmediata(9999.0); // opcional
        p.setCondicion("NUEVO");            // opcional
        p.setUbicacion("CABA");             // opcional
        p.setCantidad(1);                   // >= 1
        return p;
    }

    @Test
    void guardarYBuscarPorId_ok() {
        var guardado = repo.save(valido());
        var encontrado = repo.findById(guardado.getIdProducto());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Mouse Gamer");
    }

    @Test
    void findAll_devuelveLista() {
        repo.save(valido());
        repo.save(valido());
        assertThat(repo.findAll()).hasSize(2);
    }
}
