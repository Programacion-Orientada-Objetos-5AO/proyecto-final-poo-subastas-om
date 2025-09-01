package ar.edu.huergo.fastbid.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Size(max = 1000, message = "La descripción no puede superar 1000 caracteres")
    private String descripcion;

    @Positive(message = "El precio inicial debe ser mayor a 0")
    private double precioInicial;

    @ElementCollection
    @CollectionTable(name = "producto_imagenes", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "imagen_url")
    private List<String> imagenes;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

    @NotBlank(message = "El estado es obligatorio")
    private String estado; // Ej: ACTIVO, FINALIZADO, CANCELADO

    @NotNull(message = "La fecha de publicación es obligatoria")
    private LocalDateTime fechaPublicacion;

    @NotNull(message = "La fecha de finalización es obligatoria")
    private LocalDateTime fechaFin;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId; // referencia a Usuario

    private Long subastaId; 

    @Positive(message = "El precio de compra inmediata debe ser mayor a 0")
    private Double precioCompraInmediata;

    private String condicion; // NUEVO, USADO, REACONDICIONADO

    private String ubicacion; // Ciudad / Pais

    @Min(value = 1, message = "La cantidad mínima debe ser 1")
    private int cantidad;
}