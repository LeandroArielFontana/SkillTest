package com.example.demo.servicios;

import com.example.demo.dto.TematicaDTO;
import com.example.demo.entidades.Categoria;
import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ValidacionCampExcepcion;
import com.example.demo.repositorios.TematicaRepositorio;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TematicaServicio {

    private final TematicaRepositorio tematicaRepositorio;
    private final ExamenServicio examenServicio;

    @Transactional
    public void crearTematica(String nombre, Categoria categoria) throws ValidacionCampExcepcion {
        Tematica tematica = new Tematica();


        tematica.setNombre(nombre);
        tematica.setCategoria(categoria);
        tematicaRepositorio.save(tematica);
    }

    @Transactional
    public void modificarTematica(String nombre,Integer id,Categoria categoria) throws ObjetoNulloExcepcion, ValidacionCampExcepcion {
        TematicaDTO tematicaDTO = obtenerPorId(id);
        tematicaDTO.setNombre(nombre);
        tematicaDTO.setCategoria(categoria);
        tematicaRepositorio.save(Mapper.tematicaDTOAEntidad(tematicaDTO));
    }

    @Transactional
    public List<Tematica> mostrarTematicas() {
        return tematicaRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tematica> mostrarTematicasPorAlta(Boolean alta)
    {
        return tematicaRepositorio.mostrarPorAlta(alta);
    }

    @Transactional(readOnly = true)
    public TematicaDTO obtenerPorId(Integer id)throws ObjetoNulloExcepcion{
        TematicaDTO tematicaDTO = Mapper.tematicaEntidadADTO(tematicaRepositorio.findById(id).orElse(null));

        if(tematicaDTO == null){
            throw new ObjetoNulloExcepcion("No se ha encontrado la tematica");
        }

        return tematicaDTO;
    }

    @Transactional
    public void eliminar(Integer id) throws ObjetoNulloExcepcion {
        TematicaDTO tematicaDTO =  Mapper.tematicaEntidadADTO(tematicaRepositorio.findById(id).orElse(null));

        for (Examen examen: tematicaDTO.getExamen()) {
            if(examen.getAlta()){
                examenServicio.eliminar(examen.getId());
            }
        }

        tematicaRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(Integer id) throws ObjetoNulloExcepcion {
        tematicaRepositorio.darAlta(id);
    }

    public List<Tematica> obtenenetTematicaPorCat(Integer categoriaId){
        return tematicaRepositorio.mostrarPorCategoria(categoriaId);
    }

    @Transactional
    public TematicaDTO tematicaDetalles(int id) throws ObjetoNulloExcepcion {
        TematicaDTO tematicaDTO = obtenerPorId(id);

        tematicaDTO.getExamen().removeIf(e -> !e.getAlta());

        return tematicaDTO;
    }
}
