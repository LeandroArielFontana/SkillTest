package com.example.demo.repositorios;

import com.example.demo.entidades.Examen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenRepositorio extends JpaRepository<Examen, Integer>{

    @Query("SELECT e FROM Examen e WHERE e.alta = :alta")
    List<Examen> mostrarPorAlta(@Param("alta")boolean Alta);

    @Modifying
    @Query("UPDATE Examen e SET e.alta= true WHERE e.id = :id")
    void darAlta(@Param("id") Integer id);

    @Query(value = "SELECT e.* FROM Examen e ORDER BY e.id DESC LIMIT 1", nativeQuery = true)
    Examen mostrarUltimoExamen();

    @Query(value ="SELECT examen_id FROM resultado GROUP BY examen_id ORDER BY count(*) DESC LIMIT 20", nativeQuery = true)
    List<Integer> examenesMasBuscados();

    @Query(value ="SELECT count(*) FROM resultado GROUP BY examen_id ORDER BY count(*) DESC LIMIT 20", nativeQuery = true)
    List<Integer> cuentaExamenesMasBuscados();

    @Query(value = "SELECT r.id FROM Resultado r WHERE r.examen_id = :id ORDER BY r.puntaje_Final DESC, r.duracion ASC LIMIT 5", nativeQuery = true)
    List<Integer> top5 (@Param("id") int id);

}
