package ar.edu.huergo.fastbid.repository;

import ar.edu.huergo.fastbid.entity.security.Usuario;
import ar.edu.huergo.fastbid.repository.security.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void findByUsername_ok() throws Exception {
        Usuario u = new Usuario();
        var un = Usuario.class.getDeclaredField("username");
        un.setAccessible(true);
        un.set(u, "test@fastbid.com");
        var pw = Usuario.class.getDeclaredField("password");
        pw.setAccessible(true);
        pw.set(u, "hash");
        usuarioRepository.save(u);

        var opt = usuarioRepository.findByUsername("test@fastbid.com");
        assertThat(opt).isPresent();
    }
}
