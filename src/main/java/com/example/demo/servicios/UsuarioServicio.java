package com.example.demo.servicios;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.dto.UsuarioInformacionDTO;
import com.example.demo.entidades.Rol;
import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.UsuarioRepositorio;
import com.example.demo.utilidades.Mapper;
import com.example.demo.utilidades.Utilidad;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioServicio implements UserDetailsService {

    private final EmailServicio emailServicio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final RolServicio rolServicio;
    private final BCryptPasswordEncoder encoder;
    private final String MENSAJE = "El nombre de usuario ingresado no existe %s";

    @Transactional
    public void crearUsuario(String nombre, String apellido, String nombreUsuario, String contrasenia, Integer edad, String mail, String telefono){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre(nombre);
        usuarioDTO.setApellido(apellido);
        usuarioDTO.setNombreUsuario(nombreUsuario);
        usuarioDTO.setContrasenia(encoder.encode(contrasenia));
        usuarioDTO.setEdad(edad);
        usuarioDTO.setMail(mail);
        usuarioDTO.setTelefono(telefono);
        usuarioDTO.setRol(rolServicio.mostrarPorNombre("USER"));
        emailServicio.enviarNuevoUsuario(usuarioDTO);
        usuarioRepositorio.save(Mapper.usuarioDTOAEntidad(usuarioDTO));
    }

    @Transactional
    public void modificarUsuario(Integer id, String nombre, String apellido, String nombreUsuario, Integer edad , String mail, String telefono) throws ObjetoNulloExcepcion{
        UsuarioDTO usuarioDTO = obtenerPorId(id);
        usuarioDTO.setNombre(nombre);
        usuarioDTO.setApellido(apellido);
        usuarioDTO.setNombreUsuario(nombreUsuario);
        usuarioDTO.setEdad(edad);
        usuarioDTO.setMail(mail);
        usuarioDTO.setTelefono(telefono);
        usuarioRepositorio.save(Mapper.usuarioDTOAEntidad(usuarioDTO));
    }

    @Transactional
    public void modificarRolUsuario(Integer id) throws ObjetoNulloExcepcion{
        UsuarioDTO usuarioDTO = obtenerPorId(id);

        if(usuarioDTO.getRol().getNombre().equalsIgnoreCase("USER")){
            usuarioDTO.setRol(rolServicio.mostrarPorNombre("ADMIN"));
        }else{
            usuarioDTO.setRol(rolServicio.mostrarPorNombre("USER"));
        }

        usuarioRepositorio.save(Mapper.usuarioDTOAEntidad(usuarioDTO));
    }

    @Transactional
    public void modificarPass(Integer id,String contraseniaAnterior, String contrasenia1, String contrasenia2) throws ObjetoNulloExcepcion{
        UsuarioDTO usuarioDTO = obtenerPorId(id);

        if(encoder.matches(contraseniaAnterior, usuarioDTO.getContrasenia()) && contrasenia1.equals(contrasenia2)){
            usuarioDTO.setContrasenia(encoder.encode(contrasenia1));
            usuarioRepositorio.save(Mapper.usuarioDTOAEntidad(usuarioDTO));
        }else{
            throw new ObjetoNulloExcepcion("Error al modificar contraseña");
        }
    }

    @Transactional
    public void generarPass(String mail) throws ObjetoNulloExcepcion{
        UsuarioDTO usuarioDTO = obtenerPorMail(mail);
        String contrasenia = Utilidad.generadorDeCadenas();
        usuarioDTO.setContrasenia(encoder.encode(contrasenia));
        emailServicio.enviarCambioPass(Mapper.usuarioDTOAEntidad(usuarioDTO), contrasenia);
        usuarioRepositorio.save(Mapper.usuarioDTOAEntidad(usuarioDTO));
    }

    @Transactional
    public List<UsuarioDTO> mostrarUsuarios() {
        return Mapper.listaUsuarioEntidadADTO(usuarioRepositorio.findAll());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> mostrarUsuariosPorAlta(Boolean alta){
        return Mapper.listaUsuarioEntidadADTO(usuarioRepositorio.mostrarPorAlta(alta));
    }

    @Transactional
    public List<UsuarioDTO> mostrarUsuariosPorRolYAlta(Rol rol,Boolean alta){
        return Mapper.listaUsuarioEntidadADTO(usuarioRepositorio.mostrarPorRolYAlta(rol,alta));
    }

    @Transactional
    public UsuarioDTO obtenerPorId (Integer id) throws ObjetoNulloExcepcion {
        UsuarioDTO usuarioDTO = Mapper.usuarioEntidadADTO(usuarioRepositorio.findById(id).orElse(null));

        if (usuarioDTO == null) {
            throw new ObjetoNulloExcepcion("No se encontro el usuario");
        }
        usuarioDTO.getResultados().removeIf(r -> r.getPuntajeFinal()==null);

        return usuarioDTO;
    }

    @Transactional
    public UsuarioDTO obtenerPorMail (String mail) throws ObjetoNulloExcepcion {
        UsuarioDTO usuarioDTO = Mapper.usuarioEntidadADTO(usuarioRepositorio.findByMail(mail).orElse(null));

        if (usuarioDTO == null) {
            throw new ObjetoNulloExcepcion("No se encontro el usuario");
        }

        return usuarioDTO;
    }

    @Transactional
    public void eliminar(Integer id) throws ObjetoNulloExcepcion{
        UsuarioDTO usuarioDTO = obtenerPorId(id);
        usuarioRepositorio.deleteById(id);
    }

    @Transactional
    public void darAlta(Integer id) throws ObjetoNulloExcepcion{
        UsuarioDTO usuarioDTO = obtenerPorId(id);
        usuarioRepositorio.darAlta(id);
    }

    @Transactional(readOnly = true)
    public UsuarioInformacionDTO informacionUsuario(Integer id) throws ObjetoNulloExcepcion{
        return Mapper.usuarioDTOAUsuarioInformacionDTO(obtenerPorId(id));
    }

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepositorio.findByNombreUsuarioAndAltaTrue(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(MENSAJE, nombreUsuario)));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        try {
            /*
            session.setAttribute("id" , usuarioDTO.getId());
            session.setAttribute("rol" , usuarioDTO.getRol().getNombre());
            session.setAttribute("usuarioEnSesion", usuarioDTO);
            */
            session.setAttribute("id" , usuario.getId());
            session.setAttribute("rol" , usuario.getRol().getNombre());
            session.setAttribute("usuarioEnSesion", usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new User(usuario.getNombreUsuario(), usuario.getContrasenia(), Collections.singletonList(authority));
    }
}
