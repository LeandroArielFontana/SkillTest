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
public class UsuarioInformacionDTO implements Serializable {

    private Integer id;
    private Rol rol;
    private String mail;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String telefono;
    private Integer edad;
    private List<Resultado> resultados;
}
