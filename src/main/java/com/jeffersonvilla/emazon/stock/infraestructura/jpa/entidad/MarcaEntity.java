package com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_MARCA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_MARCA;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "marca")
public class MarcaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca", columnDefinition = "INT")
    private Long id;

    @Column(name = "nombre", length = TAMANO_MAXIMO_NOMBRE_MARCA, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = TAMANO_MAXIMO_DESCRIPCION_MARCA, nullable = false)
    private String descripcion;
}
