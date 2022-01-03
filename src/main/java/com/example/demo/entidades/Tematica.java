package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE Tematica t SET t.alta = false WHERE t.id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Tematica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String nombre;
    private Boolean alta;
    @OneToMany(mappedBy = "tematica")
    private List<Examen> examen;
    @ManyToOne
    private Categoria categoria;

    public Tematica() {
        this.alta = true;
    }

    public Tematica(Integer id, String nombre, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.alta = true;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Tematica{" +
                "nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tematica)) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return this.toString().toLowerCase().hashCode();
    }
}
