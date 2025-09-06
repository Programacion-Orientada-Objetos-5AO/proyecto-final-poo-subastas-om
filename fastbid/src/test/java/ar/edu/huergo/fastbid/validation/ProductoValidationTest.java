package ar.edu.huergo.fastbid.validation;

import ar.edu.huergo.fastbid.entity.Producto;
import jakarta.validation.*;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductoValidationTest {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private Producto valido() {
        Producto p = new Producto();
        // Campos obligatorios según tus anotaciones
        // idProducto generado
        // nombre (NotBlank, Size 2..100)
        // estado (NotBlank)
        // precioInicial (Positive)
        // categoriaId (NotNull)
        // fechaPublicacion/fechaFin (NotNull)
        // usuarioId (NotNull)
        // cantidad (Min 1)
        try {
            var nombreField = Producto.class.getDeclaredField("nombre");
            nombreField.setAccessible(true);
            nombreField.set(p, "Teclado gamer");

            var estadoField = Producto.class.getDeclaredField("estado");
            estadoField.setAccessible(true);
            estadoField.set(p, "ACTIVO");

            var precioField = Producto.class.getDeclaredField("precioInicial");
            precioField.setAccessible(true);
            precioField.set(p, 1000.0);

            var categoriaField = Producto.class.getDeclaredField("categoriaId");
            categoriaField.setAccessible(true);
            categoriaField.set(p, 1L);

            var pubField = Producto.class.getDeclaredField("fechaPublicacion");
            pubField.setAccessible(true);
            pubField.set(p, LocalDateTime.now());

            var finField = Producto.class.getDeclaredField("fechaFin");
            finField.setAccessible(true);
            finField.set(p, LocalDateTime.now().plusDays(7));

            var usuarioField = Producto.class.getDeclaredField("usuarioId");
            usuarioField.setAccessible(true);
            usuarioField.set(p, 99L);

            var cantField = Producto.class.getDeclaredField("cantidad");
            cantField.setAccessible(true);
            cantField.set(p, 1);

            var imgsField = Producto.class.getDeclaredField("imagenes");
            imgsField.setAccessible(true);
            imgsField.set(p, List.of("https://ejemplo/img1.png"));
        } catch (Exception ignore) { }
        return p;
    }

    @Test
    void productoValido_noViolaConstraints() {
        Set<ConstraintViolation<Producto>> v = validator.validate(valido());
        assertThat(v).isEmpty();
    }

    @Test
    void nombreVacio_falla() throws Exception {
        Producto p = valido();
        var f = Producto.class.getDeclaredField("nombre");
        f.setAccessible(true);
        f.set(p, "");
        assertThat(validator.validate(p)).isNotEmpty();
    }

    @Test
    void precioInicialNegativo_falla() throws Exception {
        Producto p = valido();
        var f = Producto.class.getDeclaredField("precioInicial");
        f.setAccessible(true);
        f.set(p, -10.0);
        assertThat(validator.validate(p)).isNotEmpty();
    }

    @Test
    void cantidadMenorA1_falla() throws Exception {
        Producto p = valido();
        var f = Producto.class.getDeclaredField("cantidad");
        f.setAccessible(true);
        f.set(p, 0);
        assertThat(validator.validate(p)).isNotEmpty();
    }
}
