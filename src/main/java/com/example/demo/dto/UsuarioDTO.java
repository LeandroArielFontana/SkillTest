package com.example.demo.dto;

import com.example.demo.entidades.Resultado;
import com.example.demo.entidades.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {

    private Integer id;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String telefono;
    private Integer edad;
    private String mail;
    private String contrasenia;
    private Rol rol;
    private List<Resultado> resultados;
}
