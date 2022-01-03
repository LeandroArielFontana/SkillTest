package com.example.demo.dto;

import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultadoDTO implements Serializable {
    private Integer Id;
    private Examen examen;
    private Usuario usuario;
    private Short respuestasCorrectas;
    private Short respuestasIncorrectas;
    private String duracion;
    private Integer puntajeFinal;
    private LocalDateTime tiempoInicio;
    private Boolean alta;
    private Boolean aprobado;
}
