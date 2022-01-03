package com.example.demo.dto;

import com.example.demo.entidades.Tematica;
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
public class CategoriaDTO implements Serializable {

    private Integer id;
    private String nombre;
    private Boolean alta;
    private List<Tematica> tematicas;
}
