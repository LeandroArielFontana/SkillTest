package com.example.demo.controladores;


import com.example.demo.entidades.Rol;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.servicios.RolServicio;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/rol")
public class RolControlador {

    private final RolServicio rolServicio;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarRoles(HttpServletRequest request, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("rol");
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null){
            mav.addObject("error", map.get("error"));
            //mav.addObject("exito", map.get("success"));
        }

        mav.addObject("roles", rolServicio.mostrarRolesPorAlta(true));
        mav.addObject("titulo", "Tabla de roles");
        return mav;
    }

    @GetMapping("/baja")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarRolesBaja() {
        ModelAndView mav = new ModelAndView("rol"); //Falta crear
        mav.addObject("roles", rolServicio.mostrarRolesPorAlta(false));
        mav.addObject("titulo", "Tabla de examenes baja");
        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearRol() {
        ModelAndView mav = new ModelAndView("rol-formulario");//Falta crear
        mav.addObject("rol", new Rol());
        mav.addObject("titulo", "Crear Rol");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarRol(@PathVariable int id, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("rol-formulario"); // Falta crear

        try {
            mav.addObject("rol", rolServicio.obtenerPorId(id));
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        mav.addObject("titulo", "editar Examen");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardar(@RequestParam String nombre, RedirectAttributes attributes) {

        try{
            rolServicio.crearRol(nombre);
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("error", repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("error", eliminado.getMessage());
        }

        return new RedirectView("/roles");
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar(@RequestParam int id, @RequestParam String nombre, RedirectAttributes attributes) {

        try {
            rolServicio.modificarRol(id,nombre);
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("error", repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("error", eliminado.getMessage());
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/roles");
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminar(@PathVariable int id, RedirectAttributes attributes) {

        try {
            rolServicio.eliminar(id);
        }catch (ObjetoNulloExcepcion nulo){
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/roles");
    }

    @PostMapping("/recuperar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView recuperar(@PathVariable int id, RedirectAttributes attributes) {

        try {
            rolServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/roles");
    }

}
