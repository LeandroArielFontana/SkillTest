package com.example.demo.utilidades;

import com.example.demo.dto.*;
import com.example.demo.entidades.*;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    public static UsuarioDTO usuarioEntidadADTO (Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setMail(usuario.getMail());
        usuarioDTO.setEdad(usuario.getEdad());
        usuarioDTO.setContrasenia(usuario.getContrasenia());
        usuarioDTO.setRol(usuario.getRol());
        usuarioDTO.setResultados(usuario.getResultados());
        usuarioDTO.setId(usuario.getId());
        return usuarioDTO;
    }

    public static Usuario usuarioDTOAEntidad (UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setMail(usuarioDTO.getMail());
        usuario.setEdad(usuarioDTO.getEdad());
        usuario.setContrasenia(usuarioDTO.getContrasenia());
        usuario.setRol(usuarioDTO.getRol());
        usuario.setResultados(usuarioDTO.getResultados());
        usuario.setId(usuarioDTO.getId());
        return usuario;
    }

    public static UsuarioInformacionDTO usuarioDTOAUsuarioInformacionDTO(UsuarioDTO usuarioDTO){
        UsuarioInformacionDTO usuarioInformacionDTO = new UsuarioInformacionDTO();
        usuarioInformacionDTO.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuarioInformacionDTO.setApellido(usuarioDTO.getApellido());
        usuarioInformacionDTO.setMail(usuarioDTO.getMail());
        usuarioInformacionDTO.setNombre(usuarioDTO.getNombre());
        usuarioInformacionDTO.setEdad(usuarioDTO.getEdad());
        usuarioInformacionDTO.setResultados(usuarioDTO.getResultados());
        usuarioInformacionDTO.setTelefono(usuarioDTO.getTelefono());
        usuarioInformacionDTO.setId(usuarioDTO.getId());
        return usuarioInformacionDTO;
    }

    public static List<UsuarioDTO> listaUsuarioEntidadADTO(List<Usuario> usuarios){
        List<UsuarioDTO> usuarioDTOS = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            usuarioDTOS.add(usuarioEntidadADTO(usuario));
        }

        return usuarioDTOS;
    }

    public static Rol rolDTOAEntidad(RolDTO rolDTO){
        Rol rol = new Rol();
        rol.setNombre(rolDTO.getNombre());
        rol.setId(rolDTO.getId());
        return rol;
    }

    public static RolDTO rolEntidadADTO(Rol rol){
        RolDTO rolDTO = new RolDTO();
        rolDTO.setNombre(rol.getNombre());
        rolDTO.setId(rol.getId());
        return rolDTO;
    }

    public static List<RolDTO> listaRolEntidadADTO (List<Rol> roles){
        List<RolDTO> rolesDTO = new ArrayList<>();

        for (Rol rol: roles) {
               rolesDTO.add(rolEntidadADTO(rol));
        }

        return rolesDTO;
    }

    public static Categoria categoriaDTOAEntidad(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId());
        categoria.setNombre(categoriaDTO.getNombre());
        return categoria;
    }

    public static CategoriaDTO categoriaEntidadADTO(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(categoria.getId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setTematicas(categoria.getTematicas());
        return categoriaDTO;
    }

    public static List<CategoriaDTO> listaCategoriaEntidadADTO (List<Categoria> categorias){
        List<CategoriaDTO> categoriasDTO = new ArrayList<>();

        for (Categoria categoria: categorias) {
            categoriasDTO.add(categoriaEntidadADTO(categoria));
        }

        return categoriasDTO;
    }

    public static Examen examenDTOAEntidad(ExamenDTO examenDTO){
        Examen examen = new Examen();
        examen.setId(examenDTO.getId());
        examen.setDificultad(examenDTO.getDificultad());
        examen.setNombre(examenDTO.getNombre());
        examen.setTematica(examenDTO.getTematica());
        examen.setResultados(examenDTO.getResultados());
        examen.setPreguntas(examenDTO.getPreguntas());
        examen.setNotaRequerida(examenDTO.getNotaRequerida());
        return examen;
    }

    public static ExamenDTO examenEntidadADTO(Examen examen){
        ExamenDTO examenDTO = new ExamenDTO();
        examenDTO.setId(examen.getId());
        examenDTO.setDificultad(examen.getDificultad());
        examenDTO.setNombre(examen.getNombre());
        examenDTO.setTematica(examen.getTematica());
        examenDTO.setResultados(examen.getResultados());
        examenDTO.setPreguntas(examen.getPreguntas());
        examenDTO.setNotaRequerida(examen.getNotaRequerida());
        return examenDTO;
    }

    public static List<ExamenDTO> listaExamenEntidadADTO (List<Examen> examenes){
        List<ExamenDTO> examenesDTO = new ArrayList<>();

        for (Examen examen: examenes) {
            examenesDTO.add(examenEntidadADTO(examen));
        }

        return examenesDTO;
    }

    public static List<Examen> listaExamenDTOAEntidad (List<ExamenDTO> examenesDTO){
        List<Examen> examenes = new ArrayList<>();

        for (ExamenDTO examenDTO: examenesDTO) {
            examenes.add(examenDTOAEntidad(examenDTO));
        }

        return examenes;
    }

    public static PreguntaDTO preguntaEntidadADTO(Pregunta pregunta){
        PreguntaDTO preguntaDTO = new PreguntaDTO();
        preguntaDTO.setId(pregunta.getId());
        preguntaDTO.setExamen(pregunta.getExamen());
        preguntaDTO.setEnunciado(pregunta.getEnunciado());
        preguntaDTO.setRespuestas(pregunta.getRespuestas());
        preguntaDTO.setRespuestaCorrecta(pregunta.getRespuestaCorrecta());
        preguntaDTO.setPuntaje(pregunta.getPuntaje());
        return preguntaDTO;
    }

    public static Pregunta preguntaDTOAEntidad(PreguntaDTO preguntaDTO){
        Pregunta pregunta = new Pregunta();
        pregunta.setId(preguntaDTO.getId());
        pregunta.setExamen(preguntaDTO.getExamen());
        pregunta.setEnunciado(preguntaDTO.getEnunciado());
        pregunta.setRespuestas(preguntaDTO.getRespuestas());
        pregunta.setRespuestaCorrecta(preguntaDTO.getRespuestaCorrecta());
        pregunta.setPuntaje(preguntaDTO.getPuntaje());
        return pregunta;
    }

    public static List<PreguntaDTO> listaPreguntaEntidadADTO (List<Pregunta> preguntas){
        List<PreguntaDTO> preguntasDTO = new ArrayList<>();

        for (Pregunta preugunta : preguntas) {
            preguntasDTO.add(preguntaEntidadADTO(preugunta));
        }

        return preguntasDTO;
    }

    public static List<Pregunta> listaPreguntaDTOAEntidad(List<PreguntaDTO> preguntasDTO){
        List<Pregunta> preguntas = new ArrayList<>();

        for (PreguntaDTO preuguntaDTO : preguntasDTO) {
            preguntas.add(preguntaDTOAEntidad(preuguntaDTO));
        }

        return preguntas;
    }

    public static ResultadoDTO resultadoEntidadADTO(Resultado resultado){
        ResultadoDTO resultadoDTO = new ResultadoDTO();
        resultadoDTO.setId(resultado.getId());
        resultadoDTO.setExamen(resultado.getExamen());
        resultadoDTO.setUsuario(resultado.getUsuario());
        resultadoDTO.setRespuestasCorrectas(resultado.getRespuestasCorrectas());
        resultadoDTO.setRespuestasIncorrectas(resultado.getRespuestasIncorrectas());
        resultadoDTO.setDuracion(resultado.getDuracion());
        resultadoDTO.setPuntajeFinal(resultado.getPuntajeFinal());
        resultadoDTO.setTiempoInicio(resultado.getTiempoInicio());
        resultadoDTO.setAprobado(resultado.getAprobado());
        return resultadoDTO;
    }

    public static Resultado resultadoDTOAEntidad(ResultadoDTO resultadoDTO){
        Resultado resultado = new Resultado();
        resultado.setId(resultadoDTO.getId());
        resultado.setExamen(resultadoDTO.getExamen());
        resultado.setUsuario(resultadoDTO.getUsuario());
        resultado.setRespuestasCorrectas(resultadoDTO.getRespuestasCorrectas());
        resultado.setRespuestasIncorrectas(resultadoDTO.getRespuestasIncorrectas());
        resultado.setDuracion(resultadoDTO.getDuracion());
        resultado.setPuntajeFinal(resultadoDTO.getPuntajeFinal());
        resultado.setTiempoInicio(resultadoDTO.getTiempoInicio());
        resultado.setAprobado(resultadoDTO.getAprobado());
        return resultado;
    }

    public static List<Resultado> listaResultadoDTOAEntidad(List<ResultadoDTO> resultadosDTO){
        List<Resultado> resultados = new ArrayList<>();

        for (ResultadoDTO resultadoDTO : resultadosDTO) {
            resultados.add(resultadoDTOAEntidad(resultadoDTO));
        }

        return resultados;
    }

    public static List<ResultadoDTO> listaResultadoEntidadADTO(List<Resultado> resultados){
        List<ResultadoDTO> resultadosDTO = new ArrayList<>();

        for (Resultado resultado : resultados) {
            resultadosDTO.add(resultadoEntidadADTO(resultado));
        }

        return resultadosDTO;
    }

    public static Tematica tematicaDTOAEntidad(TematicaDTO tematicaDTO){
        Tematica tematica = new Tematica();
        tematica.setId(tematicaDTO.getId());
        tematica.setNombre(tematicaDTO.getNombre());
        tematica.setExamen(tematicaDTO.getExamen());
        tematica.setCategoria(tematicaDTO.getCategoria());
        return tematica;
    }

    public static TematicaDTO tematicaEntidadADTO(Tematica tematica){
        TematicaDTO tematicaDTO = new TematicaDTO();
        tematicaDTO.setId(tematica.getId());
        tematicaDTO.setNombre(tematica.getNombre());
        tematicaDTO.setExamen(tematica.getExamen());
        tematicaDTO.setCategoria(tematica.getCategoria());
        return tematicaDTO;
    }

    public static PreguntaEditableDTO preguntaDTOAPreguntaEditableDTO(PreguntaDTO preguntaDTO){
        PreguntaEditableDTO preguntaEditableDTO = new PreguntaEditableDTO();
        preguntaEditableDTO.setRespuestaCorrecta(preguntaDTO.getRespuestaCorrecta());
        preguntaEditableDTO.setRespuesta2(preguntaDTO.getRespuestas().get(1));
        preguntaEditableDTO.setRespuesta3(preguntaDTO.getRespuestas().get(2));
        preguntaEditableDTO.setRespuesta4(preguntaDTO.getRespuestas().get(3));
        preguntaEditableDTO.setEnunciado(preguntaDTO.getEnunciado());
        preguntaEditableDTO.setPuntaje(preguntaDTO.getPuntaje());
        preguntaEditableDTO.setExamen(preguntaDTO.getExamen());
        preguntaEditableDTO.setId(preguntaDTO.getId());
        return preguntaEditableDTO;
    }
}
