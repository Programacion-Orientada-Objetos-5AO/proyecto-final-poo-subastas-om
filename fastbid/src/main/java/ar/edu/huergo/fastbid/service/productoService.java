package ar.edu.huergo.fastbid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.fastbid.repository.productoRepository;

@Service
public class productoService {
    @Autowired
    private productoRepository productoRepository;

    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    public Producto obteneProductoPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto productoDetalles) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            producto.setNombre(productoDetalles.getNombre());
            producto.setDescripcion(productoDetalles.getDescripcion());
            producto.setPrecioInicial(productoDetalles.getPrecioInicial());
            return productoRepository.save(producto);
        }
        return null;
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
