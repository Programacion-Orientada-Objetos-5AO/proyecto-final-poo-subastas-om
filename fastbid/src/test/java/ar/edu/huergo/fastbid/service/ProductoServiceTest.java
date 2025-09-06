package ar.edu.huergo.fastbid.service;

import ar.edu.huergo.fastbid.entity.Producto;
import ar.edu.huergo.fastbid.repository.productoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @InjectMocks
    private productoService service;

    @Mock
    private productoRepository repo;

    @Test
    void obtenerProductos_devuelveRepoFindAll() {
        when(repo.findAll()).thenReturn(List.of(new Producto(), new Producto()));
        var lista = service.obtenerProductos();
        assertThat(lista).hasSize(2);
        verify(repo).findAll();
    }

    @Test
    void obteneProductoPorId_buscaEnRepo() {
        Producto p = new Producto();
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        var res = service.obteneProductoPorId(1L);
        assertThat(res).isSameAs(p);
        verify(repo).findById(1L);
    }

    @Test
    void crearProducto_invocaSave() {
        Producto in = new Producto();
        when(repo.save(any())).thenReturn(in);

        var out = service.crearProducto(in);
        assertThat(out).isSameAs(in);
        verify(repo).save(in);
    }

    @Test
    void actualizarProducto_cargaEdicionYGuarda() {
        Producto original = new Producto();
        Producto cambios = new Producto();
        when(repo.findById(5L)).thenReturn(Optional.of(original));
        when(repo.save(any())).thenReturn(original);

        var actualizado = service.actualizarProducto(5L, cambios);
        assertThat(actualizado).isNotNull();
        verify(repo).findById(5L);
        verify(repo).save(any(Producto.class));
    }

    @Test
    void eliminarProducto_invocaDeleteById() {
        service.eliminarProducto(9L);
        verify(repo).deleteById(9L);
    }
}
