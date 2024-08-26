package com.jeffersonvilla.emazon.stock.configuracion;

import com.jeffersonvilla.emazon.stock.dominio.api.IMarcaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.servicio.MarcaCasoUso;
import com.jeffersonvilla.emazon.stock.dominio.spi.IMarcaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.MarcaMapperJPA;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio.MarcaPersistenciaJPA;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MarcaConfig {

    private final MarcaRepository marcaRepository;
    private final MarcaMapperJPA marcaMapperJPA;

    @Bean
    public IMarcaServicePort marcaServicePort(){
        return new MarcaCasoUso(marcaPersistenciaPort());
    }

    @Bean
    public IMarcaPersistenciaPort marcaPersistenciaPort(){
        return new MarcaPersistenciaJPA(marcaRepository, marcaMapperJPA);
    }
}
