package com.example.demo.entidades;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE Usuario u SET u.alta = false WHERE u.id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Rol rol;
    @Column(nullable = false, unique = true)
    private String mail;
    @Column(nullable = false , unique=true)
    private String nombreUsuario;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    private String telefono;
    private Integer edad;
    @Column(nullable = false)
    private String contrasenia;
    @OneToMany(mappedBy = "usuario")
    private List<Resultado> resultados;

    @CreatedDate
    @Column(nullable = false , updatable = false)
    private Date fechaCreacion;
    @LastModifiedDate
    private Date fechaModificacion;
    private Boolean alta;

    public Usuario() {
        this.alta = true;
    }

    public Usuario(Integer id, Rol rol, String mail, String nombreUsuario, String contrasenia, String apellido, Integer edad, String nombre) {
        this.id = id;
        this.rol = rol;
        this.mail = mail;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.alta = true;
        this.apellido = apellido;
        this.edad = edad;
        this.nombre = nombre;
    }
}
