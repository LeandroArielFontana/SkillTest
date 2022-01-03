package com.example.demo.controladores;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entidades.Rol;
import com.example.demo.entidades.Usuario;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.repositorios.UsuarioRepositorio;
import com.example.demo.servicios.RolServicio;
import com.example.demo.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;
    private final RolServicio rolServicio;
    private final UsuarioRepositorio usuarioRepositorio; //PROBAR QUE ONDA

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarUsuarios(){
        ModelAndView mav = new ModelAndView("usuario");//
        mav.addObject("usuarios", usuarioServicio.mostrarUsuariosPorRolYAlta(rolServicio.mostrarPorNombre("USER"),true));
        mav.addObject("admins", usuarioServicio.mostrarUsuariosPorRolYAlta(rolServicio.mostrarPorNombre("ADMIN"),true));
        List<UsuarioDTO> usuariosDTOADMIN = usuarioServicio.mostrarUsuariosPorRolYAlta(rolServicio.mostrarPorNombre("ADMIN"),false);
        List<UsuarioDTO> usuariosDTOUSER = usuarioServicio.mostrarUsuariosPorRolYAlta(rolServicio.mostrarPorNombre("USER"),false);
        mav.addObject("usuariosEliminados", usuariosDTOADMIN);
        mav.addObject("adminsEliminados", usuariosDTOUSER);
        mav.addObject("eliminados", usuariosDTOADMIN.size() + usuariosDTOUSER.size() == 0);
        mav.addObject("titulo", "Tabla de Usuarios");
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView obtenerPerfil(@PathVariable int id, RedirectAttributes attributes, HttpSession session){
        ModelAndView mav = new ModelAndView("perfil");
        try{
            if ((int)session.getAttribute("id") != id && !session.getAttribute("rol").equals("ADMIN")){
                attributes.addFlashAttribute("error", "No se puede acceder al perfil solicitado");
                mav.setViewName("redirect:/");
                return mav;
            }
            mav.addObject("usuario",usuarioServicio.obtenerPorId(id));
        }catch( ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }
        mav.addObject("titulo", "Mi perfil");

        return mav;
    }

    @GetMapping("/crear")
    public ModelAndView crearUsuario(){
        ModelAndView mav = new ModelAndView("registerDonato");
        mav.addObject("usuario", new Usuario());
        mav.addObject("roles", rolServicio.mostrarRolesPorAlta(true));
        mav.addObject("titulo", "Crear Usuario");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarUsuario(@PathVariable Integer id, HttpSession sesion, RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("usuario-editar");

        try {
            if((int)sesion.getAttribute("id") != id && !usuarioServicio.obtenerPorId(id).getRol().toString().equalsIgnoreCase("ADMIN") ){
                mav.setViewName("redirect:/");
                attributes.addFlashAttribute("error", "No se encuentra habilitado para realizar la modificación");
                return mav;
            }

            mav.addObject("roles", rolServicio.mostrarRoles());
            mav.addObject("rolUsuario", rolServicio.mostrarPorNombre("USUARIO"));
            mav.addObject("usuario", usuarioServicio.obtenerPorId(id));
        }catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        mav.addObject("titulo", "Editar Usuario");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @GetMapping("/editarPass/{id}")
    public ModelAndView editarPass(@PathVariable Integer id, HttpSession sesion, RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("usuario-contrasenia");

        try {
            if((int)sesion.getAttribute("id") != id && !usuarioServicio.obtenerPorId(id).getRol().toString().equalsIgnoreCase("ADMIN") ){
                mav.setViewName("redirect:/");
                attributes.addFlashAttribute("error", "No se encuentra habilitado para realizar la modificación");
                return mav;
            }

            mav.addObject("usuario", usuarioServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        mav.addObject("titulo", "Cambiar Contraseña");
        mav.addObject("accion", "modificarPass");
        return mav;
    }

    @GetMapping("/confirmarUsuario")
    public ModelAndView editarPass(RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("usuario-nuevaContrasenia");
        mav.addObject("titulo", "Recuperar Contraseña");
        mav.addObject("accion", "generarPass");
        return mav;
    }

    @PostMapping("/recuperarPass")
    public RedirectView generarPass(@RequestParam(name = "mail") String mail, RedirectAttributes attributes){

        try {
            usuarioServicio.generarPass(mail);
            attributes.addFlashAttribute("exito", "Se envío un mail a su casilla de correo");
        } catch (ObjetoNulloExcepcion e) {
            attributes.addFlashAttribute("error", "No se encontró un usuario con los datos provistos");
            System.out.println("ERROR ACA");
        }

        return new RedirectView("/login");
    }

    @PostMapping("/guardar")
    public RedirectView guardarUsuario(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String nombreUsuario, @RequestParam String contrasenia, @RequestParam Integer edad, @RequestParam String mail, @RequestParam String telefono) {
        usuarioServicio.crearUsuario(nombre, apellido, nombreUsuario, contrasenia, edad, mail, telefono);
        return new RedirectView("/usuario");
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam(name = "usuarioId") Integer id, @RequestParam String nombre, @RequestParam String apellido,  @RequestParam String nombreUsuario, @RequestParam Integer edad, @RequestParam String telefono, @RequestParam String mail, HttpSession sesion, RedirectAttributes attributes){
        try{
            usuarioServicio.modificarUsuario(id, nombre, apellido, nombreUsuario, edad, mail, telefono);

            if((int)sesion.getAttribute("id") == id){
                return new RedirectView("/usuario/"+id);
            }else if(usuarioServicio.obtenerPorId(id).getRol().toString().equalsIgnoreCase("ADMIN")){
                return new RedirectView("/usuario");
            }
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }
        return new RedirectView("/usuario");
    }

    @PostMapping("/modificarPass")
    public RedirectView modificarPass(@RequestParam(name = "usuarioId") Integer id, @RequestParam String contraseniaAnterior, @RequestParam String contrasenia1, @RequestParam String contrasenia2, HttpSession sesion, RedirectAttributes attributes){
        try{
            usuarioServicio.modificarPass(id,contraseniaAnterior,contrasenia1,contrasenia2);
                if((int)sesion.getAttribute("id") == id){
                    return new RedirectView("/usuario/"+id);
                }else if(usuarioServicio.obtenerPorId(id).getRol().toString().equalsIgnoreCase("ADMIN")){
                    return new RedirectView("/usuario");
                }
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }
        return new RedirectView("/usuario");
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminarUsuario(@PathVariable Integer id, RedirectAttributes attributes){
        try {
            usuarioServicio.eliminar(id);
        }catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/usuario");
    }

    @PostMapping("/cambiarRol/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView cambiarRol(@PathVariable Integer id, RedirectAttributes attributes){
        try {
            usuarioServicio.modificarRolUsuario(id);
        }catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/usuario");
    }

    @PostMapping("/darAlta/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView darAltaUsuario (@PathVariable Integer id, RedirectAttributes attributes){
        try {
            usuarioServicio.darAlta(id);
        }catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }
        return new RedirectView("/usuario");
    }
}
