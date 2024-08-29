package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.ArticuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloRepository extends JpaRepository<ArticuloEntity, Long> {
}
