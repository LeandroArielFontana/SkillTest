package com.example.demo.servicios;

import com.example.demo.dto.ExamenDTO;
import com.example.demo.dto.PreguntaDTO;
import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.excepciones.PadreNuloExcepcion;
import com.example.demo.repositorios.PreguntaRepositorio;
import com.example.demo.utilidades.Dificultad;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PreguntaServicio {

    private final PreguntaRepositorio preguntaRepositorio;
    private final ExamenServicio examenservicio;

    @Transactional
    public void crearPregunta(String enunciado, String respuesta2, String respuesta3, String respuesta4, String respuestaCorrecta, Integer puntaje, Integer idExamen) throws ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion, ObjetoNulloExcepcion, PadreNuloExcepcion{
        ExamenDTO examenDTO = examenservicio.obtenerPorId(idExamen);
        List<String> respuestas = new ArrayList<>();

        respuestas.add(respuestaCorrecta);
        respuestas.add(respuesta2);
        respuestas.add(respuesta3);
        respuestas.add(respuesta4);

        PreguntaDTO preguntaDTO = new PreguntaDTO();
        preguntaDTO.setEnunciado(enunciado);
        preguntaDTO.setRespuestas(respuestas);
        preguntaDTO.setRespuestaCorrecta(respuestaCorrecta);
        preguntaDTO.setPuntaje(puntaje);

        try {
            preguntaDTO.setExamen(Mapper.examenDTOAEntidad(examenservicio.obtenerPorId(examenDTO.getId())));
        } catch (ObjetoNulloExcepcion nulo) {
            throw new PadreNuloExcepcion("Error al buscar el Examen");
        }

        if(mostrarPreguntasPorAlta(true).contains(preguntaDTO)) {
            throw new ObjetoRepetidoExcepcion("Se encontró una pregunta con el mismo enunciado");
        }
        preguntaRepositorio.save(Mapper.preguntaDTOAEntidad(preguntaDTO));
    }

    @Transactional
    public void modificarPregunta(String enunciado, String respuesta2, String respuesta3, String respuesta4, String respuestaCorrecta, Integer puntaje, Examen examen, Integer id) throws ObjetoNulloExcepcion, ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion {
        PreguntaDTO preguntaDTO = obtenerPorId(id);
        PreguntaDTO preguntaAux = preguntaDTO;

        List<String> respuestas = new ArrayList<>();

        respuestas.add(respuestaCorrecta);
        respuestas.add(respuesta2);
        respuestas.add(respuesta3);
        respuestas.add(respuesta4);

        preguntaDTO.setEnunciado(enunciado);
        preguntaDTO.setRespuestas(respuestas);
        preguntaDTO.setRespuestaCorrecta(respuestaCorrecta);
        preguntaDTO.setPuntaje(puntaje);

        if(!preguntaAux.equals(preguntaDTO)){
            if(mostrarPreguntasPorAlta(true).contains(preguntaDTO)) {
                throw new ObjetoRepetidoExcepcion("Se encontró una pregunta con el mismo enunciado");
            }
        }

        preguntaRepositorio.save(Mapper.preguntaDTOAEntidad(preguntaDTO));
    }

    @Transactional
    public List<PreguntaDTO> mostrarPreguntas() {
        return Mapper.listaPreguntaEntidadADTO(preguntaRepositorio.findAll());
    }

    @Transactional
    public List<PreguntaDTO> mostrarPreguntasPorAlta(Boolean alta) {
        return Mapper.listaPreguntaEntidadADTO(preguntaRepositorio.mostrarPorAlta(alta));
    }

    @Transactional
    public PreguntaDTO obtenerPorId(int id) throws ObjetoNulloExcepcion {
        PreguntaDTO preguntaDTO = Mapper.preguntaEntidadADTO(preguntaRepositorio.findById(id).orElse(null));

        if (preguntaDTO == null) {
            throw new ObjetoNulloExcepcion("No se encontro la pregunta");
        }

        return preguntaDTO;
    }

    @Transactional
    public void eliminar(Integer id) throws ObjetoNulloExcepcion {
        PreguntaDTO preguntaDTO = obtenerPorId(id);
        preguntaRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        PreguntaDTO preguntaDTO = obtenerPorId(id);
        preguntaRepositorio.darAlta(id);
    }
}
