package ar.edu.huergo.fastbid.controller.security;

import ar.edu.huergo.fastbid.dto.security.RegistrarDTO;
import ar.edu.huergo.fastbid.entity.security.Usuario;
import ar.edu.huergo.fastbid.mapper.security.UsuarioMapper;
import ar.edu.huergo.fastbid.service.security.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @TestConfiguration
    static class Beans {
        @Bean UsuarioMapper usuarioMapper() { return new UsuarioMapper(); }           // @Component real
        @Bean UsuarioService usuarioService() { return Mockito.mock(UsuarioService.class); } // mock
    }

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;
    @Autowired UsuarioService usuarioService; // mock

    @Test
    void registrar_ok200() throws Exception {
        RegistrarDTO in = new RegistrarDTO("user@mail.com","Passw0rd!","Passw0rd!");
        Usuario saved = new Usuario("user@mail.com", "hashed");
        saved.setId(1L);

        when(usuarioService.registrar(any(Usuario.class), eq("Passw0rd!"), eq("Passw0rd!")))
                .thenReturn(saved);

        mvc.perform(post("/api/usuarios/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(in)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.username").value("user@mail.com"));
    }

    @Test
    void listar_ok200() throws Exception {
        when(usuarioService.getAllUsuarios())
                .thenReturn(List.of(new Usuario("user@mail.com","x")));

        mvc.perform(get("/api/usuarios"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].username").value("user@mail.com"));
    }
}
