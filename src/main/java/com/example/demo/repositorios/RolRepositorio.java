package com.example.demo.repositorios;

import com.example.demo.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Integer> {

    @Query("SELECT r FROM Rol r WHERE r.alta = :alta")
    List<Rol> mostrarPorAlta(@Param("alta")boolean Alta);

    @Query("SELECT r FROM Rol r WHERE r.nombre = :nombreRol")
    Rol mostrarPorNombre(@Param("nombreRol")String nombre);

    @Modifying
    @Query("UPDATE Rol r SET r.alta= true WHERE r.id = :id")
    void darAlta(@Param("id") Integer id);
}
