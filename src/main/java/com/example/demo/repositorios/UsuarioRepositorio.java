package com.example.demo.repositorios;

import com.example.demo.entidades.Rol;
import com.example.demo.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.alta = :alta")
    List<Usuario> mostrarPorAlta(@Param("alta") Boolean alta);

    @Modifying
    @Query("UPDATE Usuario u SET u.alta = true WHERE u.id = :id")
    void darAlta (@Param("id") Integer id);

    @Query("SELECT u FROM Usuario u WHERE u.rol = :rol and u.alta = :alta")
    List<Usuario> mostrarPorRolYAlta(@Param("rol")Rol rol, @Param("alta") Boolean alta);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByMail(String mail);

    Optional<Usuario> findByNombreUsuarioAndAltaTrue(String nombreUsuario);

    //PRUEBA

    @Query("SELECT u FROM Usuario u WHERE u.mail = :mail")
    Usuario buscarPorMail(@Param("mail") String mail);


}
