package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.MarcaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {

    Optional<MarcaEntity> findByNombre(String nombre);
}
