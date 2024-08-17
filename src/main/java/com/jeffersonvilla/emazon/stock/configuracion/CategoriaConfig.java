package com.jeffersonvilla.emazon.stock.configuracion;

import com.jeffersonvilla.emazon.stock.dominio.api.ICategoriaServicePort;
import com.jeffersonvilla.emazon.stock.dominio.api.servicio.CategoriaCasoUso;
import com.jeffersonvilla.emazon.stock.dominio.spi.ICategoriaPersistenciaPort;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.mapper.CategoriaMapperJPA;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio.CategoriaPersistenciaJPA;
import com.jeffersonvilla.emazon.stock.infraestructura.jpa.repositorio.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CategoriaConfig {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapperJPA categoriaMapperJPA;

    @Bean
    public ICategoriaServicePort categoriaServicePort(){
        return new CategoriaCasoUso(categoriaPersistenciaPort());
    }

    @Bean
    public ICategoriaPersistenciaPort categoriaPersistenciaPort(){
        return new CategoriaPersistenciaJPA(categoriaRepository, categoriaMapperJPA);
    }
}
