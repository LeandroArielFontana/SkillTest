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
@SQLDelete(sql="UPDATE Pregunta p SET p.alta = false WHERE p.id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Pregunta{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Examen examen;
    @Column(nullable = false)
    private String enunciado;
    @ElementCollection(targetClass=String.class)
    private List<String> respuestas;
    @Column(nullable = false)
    private String respuestaCorrecta;
    private Boolean alta;
    private Integer puntaje;

    public Pregunta(Integer id, Examen examen, String enunciado, String respuestaCorrecta, Integer puntaje) {
        this.id = id;
        this.examen = examen;
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
        this.alta = true;
        this.puntaje = puntaje;
    }

    public Pregunta() {
        this.alta = true;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "examen=" + examen.getId() +
                ", enunciado='" + enunciado.toLowerCase().trim() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pregunta)) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return this.toString().toLowerCase().hashCode();
    }
}
