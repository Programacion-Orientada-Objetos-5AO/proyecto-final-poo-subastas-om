package ar.edu.huergo.fastbid.service.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenServiceTest {

    @Test
    @DisplayName("Genera, extrae y valida un JWT correcto")
    void generarYValidar() {
        // Secret larga (>=32 bytes para HS256) y expiración 1 hora
        JwtTokenService jwt = new JwtTokenService("0123456789_0123456789_0123456789_0123", 3600_000);

        var user = User.withUsername("user@mail.com").password("x").authorities("ROLE_USER").build();
        String token = jwt.generarToken(user, List.of("ROLE_USER"));

        assertNotNull(token);
        assertEquals("user@mail.com", jwt.extraerUsername(token));
        assertTrue(jwt.esTokenValido(token, user));
    }

    @Test
    @DisplayName("Token inválido para otro usuario")
    void invalidoParaOtroUsuario() {
        JwtTokenService jwt = new JwtTokenService("0123456789_0123456789_0123456789_0123", 3600_000);

        var user = User.withUsername("user@mail.com").password("x").authorities("ROLE_USER").build();
        var otro = User.withUsername("otro@mail.com").password("x").authorities("ROLE_USER").build();

        String token = jwt.generarToken(user, List.of("ROLE_USER"));

        assertFalse(jwt.esTokenValido(token, otro));
    }
}
