package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE Categoria c SET c.alta = false WHERE c.id = ?")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    private Boolean alta;
    @OneToMany(mappedBy = "categoria")
    private List<Tematica> tematicas;

    public Categoria(Integer id, String nombre, List<Tematica> tematicas) {
        this.id = id;
        this.nombre = nombre;
        this.alta = true;
        this.tematicas = tematicas;
    }

    public Categoria() {
        this.alta = true;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Categoria)) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return this.toString().toLowerCase().hashCode();
    }
}
