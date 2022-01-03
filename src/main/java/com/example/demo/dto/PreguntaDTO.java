package com.example.demo.dto;

import com.example.demo.entidades.Examen;
import com.example.demo.utilidades.Dificultad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreguntaDTO implements Serializable {
    private Integer id;
    private Examen examen;
    private Dificultad dificultad;
    private String enunciado;
    private List<String> respuestas;
    private String respuestaCorrecta;
    private Boolean alta;
    private Integer puntaje;
}
