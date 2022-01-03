package com.example.demo.servicios;

import com.example.demo.dto.ExamenDTO;
import com.example.demo.dto.PreguntaDTO;
import com.example.demo.dto.ResultadoDTO;
import com.example.demo.repositorios.ExamenRepositorio;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.ResultadoRepositorio;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ResultadoServicio {

    private final ResultadoRepositorio resultadoRepositorio;
    private final UsuarioServicio usuarioServicio;
    private final ExamenServicio examenServicio;
    private final ExamenRepositorio examenRepositorio;

    @Transactional
    public void crearResultado(ExamenDTO examenDTO, int id) throws ObjetoNulloExcepcion{
        ResultadoDTO resultadoDTO = new ResultadoDTO();
        resultadoDTO.setExamen(Mapper.examenDTOAEntidad(examenDTO));
        resultadoDTO.setUsuario(Mapper.usuarioDTOAEntidad(usuarioServicio.obtenerPorId(id)));
        resultadoDTO.setRespuestasCorrectas((short)0);
        resultadoDTO.setRespuestasIncorrectas((short)(examenDTO.getPreguntas().size()));
        resultadoDTO.setPuntajeFinal(null);
        resultadoRepositorio.save(Mapper.resultadoDTOAEntidad(resultadoDTO));
    }

    @Transactional
    public void modificarResultado(int id, List<String> respuestas, int examenId) throws ObjetoNulloExcepcion {
        short contadorRespuestasCorrectas = 0;
        short contadorRespuestasInorrectas = 0;
        short contadorRespuestasInorrectasAux = 0;
        int puntajeAcumulado = 0;
        int puntajeTotal = 0;

        ExamenDTO examenDTO = examenServicio.obtenerPorId(examenId);
        List<PreguntaDTO> preguntasDTO = Mapper.listaPreguntaEntidadADTO(examenDTO.getPreguntas());
/*

    ESTO NUNCA DABA

        for (int i=0; i< preguntasDTO.size(); i++ ){

            puntajeTotal += preguntasDTO.get(i).getPuntaje();

            System.out.println("***********************************************************************************************");
            System.out.println("El valor de i es de : " + i);
            System.out.println(preguntasDTO.get(i).getRespuestaCorrecta() + " = " + respuestas.get(i));
            System.out.println("***********************************************************************************************");

            if (preguntasDTO.get(i).getRespuestaCorrecta().equalsIgnoreCase(respuestas.get(i))){
                contadorRespuestasCorrectas++;
                puntajeAcumulado += preguntasDTO.get(i).getPuntaje();
            }else{
                contadorRespuestasInorrectas++;
            }
        }
*/
        for (int i=0; i< preguntasDTO.size(); i++){
            puntajeTotal += preguntasDTO.get(i).getPuntaje();
            contadorRespuestasInorrectasAux = 0;
            for (int j=0; j< respuestas.size(); j++) {

                System.out.println("***********************************************************************************************");
                System.out.println(preguntasDTO.get(i).getRespuestaCorrecta() + " = " + respuestas.get(j));
                System.out.println("***********************************************************************************************");

                if (preguntasDTO.get(i).getRespuestaCorrecta().equalsIgnoreCase(respuestas.get(j))){
                    contadorRespuestasCorrectas++;
                    puntajeAcumulado += preguntasDTO.get(i).getPuntaje();
                }else{
                    contadorRespuestasInorrectasAux++;
                }
            }
            if (contadorRespuestasInorrectasAux == respuestas.size()){
                contadorRespuestasInorrectas++;
            }
        }

        int puntajeFinal = Math.round(puntajeAcumulado*100/puntajeTotal);

        System.out.println("**********************************************************************************************************************");
        System.out.println("EL PUNTAJE FINAL ES DE: " + puntajeFinal);
        System.out.println("**********************************************************************************************************************");

        ResultadoDTO resultadoDTO = obtenerPorId(id);

        if (resultadoDTO.getPuntajeFinal() == null) {

            resultadoDTO.setRespuestasCorrectas(contadorRespuestasCorrectas);
            resultadoDTO.setRespuestasIncorrectas(contadorRespuestasInorrectas);
            resultadoDTO.setPuntajeFinal(puntajeFinal);
            resultadoDTO.setAprobado(puntajeFinal>examenDTO.getNotaRequerida());
            resultadoDTO.setDuracion(diferenciaTiempo(resultadoDTO.getTiempoInicio(), LocalDateTime.now()));
            resultadoRepositorio.save(Mapper.resultadoDTOAEntidad(resultadoDTO));
            System.out.println("**********************************************************************************************************************");
            System.out.println("EL PUNTAJE FUE GUARDADO CON EXITO");
            System.out.println("**********************************************************************************************************************");
        }
    }

    @Transactional
    public List<ResultadoDTO> mostrarResultados() {
       List<ResultadoDTO> resultados = Mapper.listaResultadoEntidadADTO(resultadoRepositorio.findAll());

        resultados.removeIf(r -> r.getPuntajeFinal()==null);

       return resultados;
    }

    @Transactional(readOnly = true)
    public List<ResultadoDTO> mostrarResultadosPorAlta(Boolean alta) {
        return Mapper.listaResultadoEntidadADTO(resultadoRepositorio.mostrarPorAlta(alta));
    }

    @Transactional
    public ResultadoDTO obtenerPorId(int id) throws ObjetoNulloExcepcion {
        ResultadoDTO resultadoDTO = Mapper.resultadoEntidadADTO(resultadoRepositorio.findById(id).orElse(null));

        if (resultadoDTO == null) {
            throw new ObjetoNulloExcepcion("");
        }

        return resultadoDTO;
    }

    @Transactional
    public ResultadoDTO ObtenerUltimoResultado() throws ObjetoNulloExcepcion {
        ResultadoDTO resultadoDTO = Mapper.resultadoEntidadADTO(resultadoRepositorio.mostrarUltimoResultado());

        if(resultadoDTO== null){
            throw new ObjetoNulloExcepcion("No se encontro el resultado");
        }

        return resultadoDTO;
    }

    @Transactional(readOnly = true)
    public List<ResultadoDTO> top5 (int id) throws ObjetoNulloExcepcion {
        List<ResultadoDTO> resultadosDTO = new ArrayList<>();

        for (Integer idResultado : examenRepositorio.top5(id)) {
            resultadosDTO.add(obtenerPorId(idResultado));
        }

        return resultadosDTO;
    }

    public static String diferenciaTiempo(LocalDateTime fechaInicio, LocalDateTime fechaFinal){
        String diferencia;
        Duration duracion = Duration.between(fechaInicio, fechaFinal);

        Long minutos = Math.abs(duracion.toMinutes());
        Long segundos = Math.abs(duracion.minusMinutes(duracion.toMinutes()).getSeconds());

        diferencia = String.format("%d:%02d%n", minutos , segundos);

        if (Integer.parseInt(diferencia.substring(0, 1)) > 59){
            diferencia = "1hr +";
        }

        return diferencia;
    }

    @Transactional
    public void eliminar(int id) throws ObjetoNulloExcepcion{
        ResultadoDTO resultadoDTO = obtenerPorId(id);
        resultadoRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        ResultadoDTO resultadoDTO = obtenerPorId(id);
        resultadoRepositorio.darAlta(id);
    }
}



