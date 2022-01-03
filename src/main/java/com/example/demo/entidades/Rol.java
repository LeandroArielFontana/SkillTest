package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@SQLDelete(sql="UPDATE Rol r SET r.alta = false WHERE r.id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    private Boolean alta;
    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;

    public Rol(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.alta = true;
    }

    public Rol() {
        this.alta = true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rol)) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return this.toString().toLowerCase().hashCode();
    }
}
