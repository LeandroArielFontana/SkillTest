package com.example.demo.servicios;

import com.example.demo.dto.ExamenDTO;
import com.example.demo.dto.PreguntaDTO;
import com.example.demo.dto.Top20ExamenesDTO;
import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.repositorios.ExamenRepositorio;
import com.example.demo.repositorios.PreguntaRepositorio;
import com.example.demo.utilidades.Dificultad;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ExamenServicio {

    private final ExamenRepositorio examenRepositorio;
    private final PreguntaRepositorio preguntaRepositorio;

    @Transactional
    public void crearExamen(Dificultad dificultad, Tematica tematica, Double notaRequerida, String nombre) {
        ExamenDTO examenDTO = new ExamenDTO();
        examenDTO.setDificultad(dificultad);
        examenDTO.setNombre(nombre);
        examenDTO.setTematica(tematica);
        examenDTO.setNotaRequerida(notaRequerida);
        examenRepositorio.save(Mapper.examenDTOAEntidad(examenDTO));
    }

    @Transactional
    public void modificarExamen(Integer id, Dificultad dificultad, Tematica tematica, Double notaRequerida, String nombre) throws ObjetoNulloExcepcion, ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion {
        ExamenDTO examenDTO = obtenerPorId(id);
        ExamenDTO examenAux = examenDTO;

        examenDTO.setNombre(nombre);
        examenDTO.setDificultad(dificultad);
        examenDTO.setTematica(tematica);
        examenDTO.setNotaRequerida(notaRequerida);

        if(!examenAux.equals(examenDTO)){
            if(mostrarExamenesPorAlta(true).contains(examenDTO)) {
                throw new ObjetoRepetidoExcepcion("Se encontró una pregunta con el mismo enunciado");
            }
        }

        examenRepositorio.save(Mapper.examenDTOAEntidad(examenDTO));
    }

    @Transactional
    public List<ExamenDTO> mostrarExamenes() {
        return Mapper.listaExamenEntidadADTO(examenRepositorio.findAll());
    }

    @Transactional(readOnly = true)
    public List<ExamenDTO> mostrarExamenesPorAlta(Boolean alta) {
        return Mapper.listaExamenEntidadADTO(examenRepositorio.mostrarPorAlta(alta));
    }

    @Transactional(readOnly = true)
    public List<Top20ExamenesDTO> mostrarExamenesMasBuscados() throws ObjetoNulloExcepcion {
        List<Top20ExamenesDTO> top20 = new ArrayList<Top20ExamenesDTO>();
        List<Integer> cantidades = examenRepositorio.cuentaExamenesMasBuscados();
        for (Integer id: examenRepositorio.examenesMasBuscados()) {
            top20.add(new Top20ExamenesDTO(obtenerPorId(id), cantidades.get(examenRepositorio.examenesMasBuscados().indexOf(id))));
        }
        return top20;
    }

    @Transactional
    public ExamenDTO obtenerPorId(int id) throws ObjetoNulloExcepcion {
        ExamenDTO examenDTO = Mapper.examenEntidadADTO(examenRepositorio.findById(id).orElse(null));

        if (examenDTO == null) {
            throw new ObjetoNulloExcepcion("No se encontro el examen");
        }

        return examenDTO;
    }

    @Transactional
    public ExamenDTO resolverExamen(int id) throws ObjetoNulloExcepcion {
        ExamenDTO examenDTO = obtenerPorId(id);

        examenDTO.getPreguntas().removeIf(p -> !p.getAlta());

        Collections.shuffle(examenDTO.getPreguntas());

        for (PreguntaDTO preguntaDTO : Mapper.listaPreguntaEntidadADTO(examenDTO.getPreguntas())) {
            Collections.shuffle(preguntaDTO.getRespuestas());
        }

        return examenDTO;
    }


    @Transactional
    public ExamenDTO ObtenerUltimoExamen() throws ObjetoNulloExcepcion {
        ExamenDTO examenDTO = Mapper.examenEntidadADTO(examenRepositorio.mostrarUltimoExamen());

        if(examenDTO == null){
            throw new ObjetoNulloExcepcion("No se encontro el examen");
        }

        return examenDTO;
    }

    @Transactional
    public void eliminar(int id) throws ObjetoNulloExcepcion {
        ExamenDTO examenDTO = obtenerPorId(id);

        for (Pregunta pregunta: examenDTO.getPreguntas()) {
            if(pregunta.getAlta()){
                preguntaRepositorio.deleteById(pregunta.getId());
            }
        }

        examenRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        ExamenDTO examenDTO = obtenerPorId(id);

        for (Pregunta pregunta: examenDTO.getPreguntas()) {
            if(pregunta.getAlta()){
                preguntaRepositorio.darAlta(pregunta.getId());
            }
        }

        examenRepositorio.darAlta(id);
    }

}

