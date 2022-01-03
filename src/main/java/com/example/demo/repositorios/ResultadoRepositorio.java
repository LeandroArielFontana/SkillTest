package com.example.demo.repositorios;

import com.example.demo.entidades.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoRepositorio extends JpaRepository<Resultado, Integer> {

    @Query("SELECT r FROM Resultado r WHERE alta = :alta")
    List<Resultado> mostrarPorAlta(@Param("alta") boolean alta);

    @Modifying
    @Query("UPDATE Resultado r Set r.alta = true WHERE r.id = :id")
    void darAlta (@Param("id") Integer id);

    @Query(value = "SELECT r.* FROM Resultado r ORDER BY r.id DESC LIMIT 1", nativeQuery = true)
    Resultado mostrarUltimoResultado();
}
