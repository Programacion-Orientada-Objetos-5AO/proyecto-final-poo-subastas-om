package ar.edu.huergo.fastbid.repository;

import ar.edu.huergo.fastbid.entity.security.Rol;
import ar.edu.huergo.fastbid.repository.security.RolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RolRepositoryTest {

    @Autowired
    private RolRepository rolRepository;

    @Test
    void guardarYBuscarPorNombre_ok() {
        Rol r = new Rol();
        try {
            var f = Rol.class.getDeclaredField("nombre");
            f.setAccessible(true);
            f.set(r, "CLIENTE");
        } catch (Exception ignore) { }
        rolRepository.save(r);

        assertThat(rolRepository.findByNombre("CLIENTE")).isPresent();
    }
}
