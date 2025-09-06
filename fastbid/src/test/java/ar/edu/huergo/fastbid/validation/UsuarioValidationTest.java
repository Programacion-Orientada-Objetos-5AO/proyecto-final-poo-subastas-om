package ar.edu.huergo.fastbid.validation;

import ar.edu.huergo.fastbid.entity.security.Usuario;
import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private Usuario valido() {
        Usuario u = new Usuario();
        try {
            var un = Usuario.class.getDeclaredField("username");
            un.setAccessible(true);
            un.set(u, "user@example.com");

            var pw = Usuario.class.getDeclaredField("password");
            pw.setAccessible(true);
            pw.set(u, "Secreta123!");
        } catch (Exception ignore) { }
        return u;
    }

    @Test
    void usuarioValido_ok() {
        Set<ConstraintViolation<Usuario>> v = validator.validate(valido());
        assertThat(v).isEmpty();
    }

    @Test
    void emailInvalido_falla() throws Exception {
        Usuario u = valido();
        var un = Usuario.class.getDeclaredField("username");
        un.setAccessible(true);
        un.set(u, "no-email");
        assertThat(validator.validate(u)).isNotEmpty();
    }

    @Test
    void passwordVacia_falla() throws Exception {
        Usuario u = valido();
        var pw = Usuario.class.getDeclaredField("password");
        pw.setAccessible(true);
        pw.set(u, "");
        assertThat(validator.validate(u)).isNotEmpty();
    }
}
