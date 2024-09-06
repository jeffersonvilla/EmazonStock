package com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_DESCRIPCION_CATEGORIA;
import static com.jeffersonvilla.emazon.stock.dominio.util.Constantes.TAMANO_MAXIMO_NOMBRE_CATEGORIA;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categoria")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria", columnDefinition = "INT")
    private Long id;

    @Column(name = "nombre", length = TAMANO_MAXIMO_NOMBRE_CATEGORIA, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = TAMANO_MAXIMO_DESCRIPCION_CATEGORIA, nullable = false)
    private String descripcion;
}
