package com.example.demo.dto;

import com.example.demo.entidades.Resultado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InformacionUsuarioDTO {

    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private Integer edad;
    private String mail;
    private List<Resultado> resultados;
}
