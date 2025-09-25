package ar.edu.huergo.fastbid.entity.subastas;

import java.time.OffsetDateTime;

import ar.edu.huergo.fastbid.entity.Producto;
import ar.edu.huergo.fastbid.entity.security.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subastas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subasta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSubasta;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;     // FK → Producto.id (único: 1–a–1)

    @Column(nullable = false)
    private OffsetDateTime fechaInicio;

    @Column(nullable = false)
    private OffsetDateTime fechaFin;

    @Column(nullable = false)
    private double precioInicial;

    @Column(nullable = false)
    private double precioActual;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SubastaEstado estado; // ACTIVA | FINALIZADA | CANCELADA | PROGRAMADA

    // Opcionales
    @Column(name = "ganador_id")
    private Long ganadorId;       // FK → Usuario.id (opcional)

    //@Column(nullable = false)
    private double incrementoMinimo; // Valor mínimo para superar la puja anterior

    private Double compraInmediata;  // Precio de compra directa (opcional)

    // --------- Relaciones de conveniencia (solo lectura) ----------
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_subasta_producto"))
    private Producto producto; // no usar en DTO, solo navegación

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ganador_id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_subasta_ganador"))
    private Usuario ganador; // idem
}