package com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_ARTICULO;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_ARTICULO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articulo")
public class ArticuloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo", columnDefinition = "INT")
    private Long id;

    @Column(name = "nombre", length = TAMANO_MAXIMO_NOMBRE_ARTICULO, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = TAMANO_MAXIMO_DESCRIPCION_ARTICULO, nullable = false)
    private String descripcion;

    @Column(name = "cantidad", columnDefinition = "INT", nullable = false)
    private Integer cantidad;

    @Column(name = "precio", columnDefinition = "DECIMAL(19, 4)", nullable = false)
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    private MarcaEntity marca;

    @ManyToMany
    @JoinTable(
            name = "articulo_categoria",
            joinColumns = @JoinColumn(name = "id_articulo"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private Set<CategoriaEntity> categorias;

    @Version
    @Column(name = "version", columnDefinition = "INT")
    private Integer version;
}
