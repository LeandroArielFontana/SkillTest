package com.example.demo.dto;

import com.example.demo.entidades.Pregunta;
import com.example.demo.entidades.Resultado;
import com.example.demo.entidades.Tematica;
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
public class ExamenDTO implements Serializable {
    private Integer id;
    private Dificultad dificultad;
    private String nombre;
    private Tematica tematica;
    private List<Resultado> resultados;
    private List<Pregunta> preguntas;
    private Double notaRequerida;
    private Boolean alta;
}
