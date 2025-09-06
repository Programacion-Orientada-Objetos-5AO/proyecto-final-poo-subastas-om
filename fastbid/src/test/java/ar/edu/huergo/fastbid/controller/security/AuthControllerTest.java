package ar.edu.huergo.fastbid.controller.security;

import ar.edu.huergo.fastbid.dto.security.LoginDTO;
import ar.edu.huergo.fastbid.service.security.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @TestConfiguration
    static class Beans {
        @Bean AuthenticationManager authenticationManager() { return Mockito.mock(AuthenticationManager.class); }
        @Bean UserDetailsService userDetailsService() { return Mockito.mock(UserDetailsService.class); }
        @Bean JwtTokenService jwtTokenService() { return Mockito.mock(JwtTokenService.class); }
    }

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired UserDetailsService userDetailsService;
    @Autowired JwtTokenService jwtTokenService;

    @Test
    void login_ok_devuelveToken() throws Exception {
        LoginDTO dto = new LoginDTO("user@mail.com","pass");

        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken("user@mail.com","pass", List.of()));

        when(userDetailsService.loadUserByUsername("user@mail.com"))
                .thenReturn(User.withUsername("user@mail.com").password("x").authorities("ROLE_USER").build());

        when(jwtTokenService.generarToken(any(), any()))
                .thenReturn("fake.jwt.token");

        mvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.token").value("fake.jwt.token"));
    }
}
