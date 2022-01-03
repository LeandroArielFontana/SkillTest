package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@SQLDelete(sql="UPDATE Resultado r SET r.alta = false WHERE r.id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    private Examen examen;
    @ManyToOne
    private Usuario usuario;
    private Short respuestasCorrectas;
    private Short respuestasIncorrectas;
    private String duracion;
    private Integer puntajeFinal;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime tiempoInicio;
    @LastModifiedDate
    private LocalDateTime tiempoFinalizacion;
    private Boolean alta;
    private Boolean aprobado;

    public Resultado(Integer id, Examen examen, Usuario usuario, Short respuestasCorrectas, Short respuestasIncorrectas, String duracion, Integer puntajeFinal) {
        Id = id;
        this.examen = examen;
        this.usuario = usuario;
        this.respuestasCorrectas = respuestasCorrectas;
        this.respuestasIncorrectas = respuestasIncorrectas;
        this.duracion = duracion;
        this.puntajeFinal = puntajeFinal;
        this.alta = true;
        this.aprobado = false;
    }

    public Resultado() {
        this.alta = true;
        this.aprobado = false;
    }
}
