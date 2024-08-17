package com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio;

import com.jeffersonvilla.emazon.stock.infraestructura.jpa.entidad.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    public Optional<CategoriaEntity> findByNombre(String nombre);
}
