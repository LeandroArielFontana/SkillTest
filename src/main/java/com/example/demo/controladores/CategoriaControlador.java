package com.example.demo.controladores;

import com.example.demo.dto.CategoriaDTO;
import com.example.demo.entidades.Categoria;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.servicios.CategoriaServicio;
import com.example.demo.servicios.TematicaServicio;
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
@RequestMapping("/categoria")
public class CategoriaControlador {

    private final CategoriaServicio categoriaServicio;
    private final TematicaServicio tematicaServicio;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView listarCategorias(HttpServletRequest request, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("categoria-administrador");
        Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);

        if(map != null){
            mav.addObject("error", map.get("error"));
            //mav.addObject("exito", map.get("success"));
        }

        mav.addObject("titulo", "Categorias");
        mav.addObject("categoriasBaja", categoriaServicio.mostrarCategoriasPorAlta(false));
        mav.addObject("categoriasAlta", categoriaServicio.mostrarCategoriasPorAlta(true));
        mav.addObject("tematicas", tematicaServicio.mostrarTematicas());

        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearCategoria() {
        ModelAndView mav = new ModelAndView("categoria-formulario");
        mav.addObject("categoria", new Categoria());
        mav.addObject("titulo", "Crear Categoria");
        mav.addObject("accion", "guardar");

        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarCategoria(@PathVariable int id, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("categoria-formulario");

        try {
            mav.addObject("categoria", categoriaServicio.obtenerPorId(id));
        }catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
            mav.setViewName("redirect:/categoria/admin");
            return mav;
        }

        mav.addObject("titulo", "Editar Categoria");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView categoriaDetalle(@PathVariable int id, RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("categoria-detalle"); //FALTA HTML

        try{
            CategoriaDTO categoriaDTO = categoriaServicio.obtenerPorId(id);
            mav.addObject("categoria",categoriaDTO) ;
            mav.addObject("titulo", "Detalle de " + categoriaDTO.getNombre() + "");
        }catch( ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardar(@RequestParam String nombre, RedirectAttributes attributes) {

        try {
            categoriaServicio.crearCategoria(nombre);
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("error", repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("error", eliminado.getMessage());
        }

        return new RedirectView("/categoria/admin");
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar(@RequestParam int id, @RequestParam String nombre, RedirectAttributes attributes) {

        try {
            categoriaServicio.modificarCategoria(id,nombre);
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("error", repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("error", eliminado.getMessage());
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/categoria/admin");
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminar(@PathVariable int id, RedirectAttributes attributes) {

        try {
            categoriaServicio.eliminar(id);
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/categoria/admin");
    }

    @PostMapping("/darAlta/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView darAlta(@PathVariable int id, RedirectAttributes attributes) {

        try {
            categoriaServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/categoria/admin");
    }
}
