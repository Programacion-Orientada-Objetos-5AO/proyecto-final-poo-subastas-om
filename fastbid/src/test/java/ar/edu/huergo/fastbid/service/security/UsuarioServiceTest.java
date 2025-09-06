package ar.edu.huergo.fastbid.service.security;

import ar.edu.huergo.fastbid.entity.security.Rol;
import ar.edu.huergo.fastbid.entity.security.Usuario;
import ar.edu.huergo.fastbid.repository.security.RolRepository;
import ar.edu.huergo.fastbid.repository.security.UsuarioRepository;
import ar.edu.huergo.fastbid.service.security.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock UsuarioRepository usuarioRepository;
    @Mock RolRepository rolRepository;
    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks 
    UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setUsername("user@mail.com");
    }

    @Test
    @DisplayName("Registrar OK asigna rol CLIENTE y encripta password")
    void registrar_ok() {
        when(usuarioRepository.existsByUsername("user@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("Passw0rd!")).thenReturn("hash");
        when(rolRepository.findByNombre("CLIENTE")).thenReturn(Optional.of(new Rol(null, "CLIENTE")));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            u.setId(1L);
            return u;
        });

        Usuario saved = usuarioService.registrar(usuario, "Passw0rd!", "Passw0rd!");

        assertNotNull(saved.getId());
        assertEquals("hash", saved.getPassword());
        assertTrue(saved.getRoles().stream().map(Rol::getNombre).anyMatch("CLIENTE"::equals));
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Password null dispara 'Las contraseñas no pueden ser null'")
    void registrar_passwordNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.registrar(usuario, null, null));
        assertEquals("Las contraseñas no pueden ser null", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Passwords distintas dispara 'Las contraseñas no coinciden'")
    void registrar_passwordsDistintas() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.registrar(usuario, "a", "b"));
        assertEquals("Las contraseñas no coinciden", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Usuario duplicado dispara 'El nombre de usuario ya está en uso'")
    void registrar_usuarioDuplicado() {
        when(usuarioRepository.existsByUsername("user@mail.com")).thenReturn(true);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> usuarioService.registrar(usuario, "Passw0rd!", "Passw0rd!"));
        assertEquals("El nombre de usuario ya está en uso", ex.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("getAllUsuarios devuelve lista")
    void getAllUsuarios_ok() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario("a@b.com","x")));
        var lista = usuarioService.getAllUsuarios();
        assertEquals(1, lista.size());
    }
}
