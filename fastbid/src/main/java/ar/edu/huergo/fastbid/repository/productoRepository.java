package ar.edu.huergo.fastbid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.fastbid.entity.Producto;

@Repository
public interface productoRepository extends JpaRepository<Producto, Long> {

}