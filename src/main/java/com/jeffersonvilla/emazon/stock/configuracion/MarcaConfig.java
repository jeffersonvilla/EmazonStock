package com.jeffersonvilla.emazon.stock.configuracion;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.servicio.MarcaCasoUso;
import com.jeffersonvilla.emazon.stock.dominio.spi.IMarcaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.MarcaMapperJpa;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio.MarcaPersistenciaJpa;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MarcaConfig {

    private final MarcaRepository marcaRepository;
    private final MarcaMapperJpa marcaMapperJPA;

    @Bean
    public IMarcaServicePort marcaServicePort(){
        return new MarcaCasoUso(marcaPersistenciaPort());
    }

    @Bean
    public IMarcaPersistenciaPort marcaPersistenciaPort(){
        return new MarcaPersistenciaJpa(marcaRepository, marcaMapperJPA);
    }
}
