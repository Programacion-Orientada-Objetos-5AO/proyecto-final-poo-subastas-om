package ar.edu.huergo.fastbid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productoRepository extends JpaRepository<Producto, Long> {

}