package com.example.demo.dto;

import com.example.demo.entidades.Examen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PreguntaEditableDTO {
    private Integer id;
    private String enunciado;
    private String respuestaCorrecta;
    private String respuesta2;
    private String respuesta3;
    private String respuesta4;
    private Integer puntaje;
    private Examen examen;
}
