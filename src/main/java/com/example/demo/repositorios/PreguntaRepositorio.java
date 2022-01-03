package com.example.demo.repositorios;

import com.example.demo.entidades.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepositorio extends JpaRepository<Pregunta, Integer>{

    @Query("SELECT p FROM Pregunta p WHERE alta = :alta")
    List<Pregunta> mostrarPorAlta(@Param("alta") boolean alta);

    @Modifying
    @Query("UPDATE Pregunta p Set p.alta = true WHERE p.id = :id")
    void darAlta (@Param("id") Integer id);
}