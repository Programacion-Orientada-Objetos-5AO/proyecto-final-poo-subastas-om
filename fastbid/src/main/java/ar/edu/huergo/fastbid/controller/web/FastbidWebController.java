package ar.edu.huergo.fastbid.controller.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("") // raíz web
public class FastbidWebController {

    /**
     * Página de login (form Thymeleaf).
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña inválidos.");
        }
        if (logout != null) {
            model.addAttribute("success", "Sesión cerrada correctamente.");
        }
        return "auth/login";
    }

    /**
     * Inicio: requiere autenticación (configurado en SecurityConfig).
     * Inyecta el nombre del usuario en el modelo.
     * Templates esperados:
     *   templates/index.html
     */
    @GetMapping({"/", "/index"})
        public String index(HttpServletRequest req, Model model) {
        var principal = req != null ? req.getUserPrincipal() : null;
        model.addAttribute("isAuth", principal != null);
        model.addAttribute("username", principal != null ? principal.getName() : null);
        return "index";
    }

    /**
     * Logout por GET (opcional).
     * Útil si querés un enlace <a href="/logout">Salir</a>.
     * Si preferís el POST nativo de Spring (/logout), podés quitar este método.
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes ra) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        ra.addFlashAttribute("success", "Sesión cerrada correctamente.");
        return "redirect:/login?logout";
    }
}
