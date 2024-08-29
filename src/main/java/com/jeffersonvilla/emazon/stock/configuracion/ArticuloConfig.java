package com.jeffersonvilla.emazon.stock.configuracion;

import com.jeffersonvilla.emazon.stock.dominio.api.IArticuloServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.servicio.ArticuloCasoUso;
import com.jeffersonvilla.emazon.stock.dominio.spi.IArticuloPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.ArticuloMapperJPA;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio.ArticuloPersistenciaJPA;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio.ArticuloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ArticuloConfig {

    private final ArticuloRepository articuloRepository;
    private final ArticuloMapperJPA articuloMapperJPA;

    @Bean
    public IArticuloServicePort articuloServicePort(){
        return new ArticuloCasoUso(articuloPersistenciaPort());
    }

    @Bean
    public IArticuloPersistenciaPort articuloPersistenciaPort(){
        return new ArticuloPersistenciaJPA(articuloRepository, articuloMapperJPA);
    }
}
