package com.example.demo.dto;

import com.example.demo.entidades.Categoria;
import com.example.demo.entidades.Examen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TematicaDTO implements Serializable {

    private Integer id;
    private String nombre;
    private Boolean alta;
    private List<Examen> examen;
    private Categoria categoria;
}
