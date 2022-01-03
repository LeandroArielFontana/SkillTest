package com.example.demo.servicios;

import com.example.demo.dto.RolDTO;
import com.example.demo.entidades.Rol;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.repositorios.RolRepositorio;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class RolServicio {

    private final RolRepositorio rolRepositorio;

    public void crearRol(String nombre) throws ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion {
        RolDTO rolDTO = new RolDTO();
        rolDTO.setNombre(nombre);

        if(mostrarRolesPorAlta(true).contains(rolDTO)) {
            throw new ObjetoRepetidoExcepcion("Se encontró un rol con el mismo nombre");
        }else if(mostrarRolesPorAlta(false).contains(rolDTO)) {
            throw new ObjetoEliminadoExcepcion("Se encontró un rol eliminado con el mismo nombre");
        }

        rolRepositorio.save(Mapper.rolDTOAEntidad(rolDTO));
    }

    @Transactional
    public void modificarRol(Integer id, String nombre) throws ObjetoNulloExcepcion, ObjetoRepetidoExcepcion, ObjetoEliminadoExcepcion {
        RolDTO rolDTO = obtenerPorId(id);
        rolDTO.setNombre(nombre);

        if(mostrarRolesPorAlta(true).contains(rolDTO)) {
            throw new ObjetoRepetidoExcepcion("Se encontró un rol con el mismo nombre");
        }else if(mostrarRolesPorAlta(false).contains(rolDTO)) {
            throw new ObjetoEliminadoExcepcion("Se encontró un rol eliminado con el mismo nombre");
        }

        rolRepositorio.save(Mapper.rolDTOAEntidad(rolDTO));
    }

    @Transactional
    public List<RolDTO> mostrarRoles() {
        return Mapper.listaRolEntidadADTO(rolRepositorio.findAll());
    }

    @Transactional(readOnly = true)
    public List<RolDTO> mostrarRolesPorAlta(Boolean alta) {
        return Mapper.listaRolEntidadADTO(rolRepositorio.mostrarPorAlta(true));
    }

    @Transactional
    public RolDTO obtenerPorId(int id) throws ObjetoNulloExcepcion {
        Rol rol = rolRepositorio.findById(id).orElse(null);

        if (rol == null) {
            throw new ObjetoNulloExcepcion("No se encontro el rol");
        }

        return Mapper.rolEntidadADTO(rol);
    }

    @Transactional
    public void eliminar(int id) throws ObjetoNulloExcepcion{
        RolDTO rolDTO = obtenerPorId(id);
        rolRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(int id) throws ObjetoNulloExcepcion {
        RolDTO rolDTO = obtenerPorId(id);
        rolRepositorio.darAlta(id);
    }

    public Rol mostrarPorNombre(String nombre){
       return rolRepositorio.mostrarPorNombre(nombre);
    }
}
