package com.example.demo.servicios;

import com.example.demo.dto.CategoriaDTO;
import com.example.demo.entidades.Categoria;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.repositorios.CategoriaRepositorio;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaServicio {

    private final CategoriaRepositorio categoriaRepositorio;
    private final TematicaServicio tematicaServicio;

    public void crearCategoria(String nombre) throws ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNombre(nombre);

        if(mostrarCategoriasPorAlta(true).contains(categoriaDTO)) {
            throw new ObjetoRepetidoExcepcion("Se encontró una categoria con el mismo enunciado");
        }else if(mostrarCategoriasPorAlta(false).contains(categoriaDTO)) {
            throw new ObjetoEliminadoExcepcion("Se encontró una categoria eliminada con el mismo enunciado");
        }

        categoriaRepositorio.save(Mapper.categoriaDTOAEntidad(categoriaDTO));
    }

    @Transactional
    public void modificarCategoria(Integer id, String nombre) throws ObjetoNulloExcepcion, ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion {
        CategoriaDTO categoriaDTO = obtenerPorId(id) ;
        categoriaDTO.setNombre(nombre);

        if(mostrarCategoriasPorAlta(true).contains(categoriaDTO)) {
            throw new ObjetoRepetidoExcepcion("Se encontró una categoria con el mismo enunciado");
        }else if(mostrarCategoriasPorAlta(false).contains(categoriaDTO)) {
            throw new ObjetoEliminadoExcepcion("Se encontró una categoria eliminada con el mismo enunciado");
        }

        categoriaRepositorio.save(Mapper.categoriaDTOAEntidad(categoriaDTO));
    }

    @Transactional
    public List<CategoriaDTO> mostrarCategorias() {
        return Mapper.listaCategoriaEntidadADTO(categoriaRepositorio.findAll());
    }

    @Transactional(readOnly = true)
    public List<CategoriaDTO> mostrarCategoriasPorAlta(Boolean alta) {
        return Mapper.listaCategoriaEntidadADTO(categoriaRepositorio.mostrarPorAlta(alta));
    }

    @Transactional
    public CategoriaDTO obtenerPorId(int id) throws ObjetoNulloExcepcion {
        CategoriaDTO categoriaDTO = Mapper.categoriaEntidadADTO(categoriaRepositorio.findById(id).orElse(null));

        if (categoriaDTO == null) {
            throw new ObjetoNulloExcepcion("No se encontro la categoria");
        }

        return categoriaDTO;
    }

    @Transactional
    public void eliminar(int id) throws ObjetoNulloExcepcion {
        CategoriaDTO categoriaDTO = obtenerPorId(id);

        for (Tematica tematica: categoriaDTO.getTematicas()) {
            if(tematica.getAlta()){
                tematicaServicio.eliminar(tematica.getId());
            }
        }

        categoriaRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        CategoriaDTO categoriaDTO = obtenerPorId(id);
        categoriaRepositorio.darAlta(id);
    }
}
