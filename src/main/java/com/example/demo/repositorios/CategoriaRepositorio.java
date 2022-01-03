package com.example.demo.repositorios;

import com.example.demo.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {

    @Query("SELECT c FROM Categoria c WHERE c.alta = :alta")
    List<Categoria> mostrarPorAlta(@Param("alta") boolean Alta);

    @Modifying
    @Query("UPDATE Categoria c SET c.alta = true WHERE c.id = :id")
    void darAlta(@Param("id") Integer id);
}
