package com.example.demo.controladores;

import com.example.demo.dto.ExamenDTO;
import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Tematica;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.servicios.ExamenServicio;
import com.example.demo.servicios.ResultadoServicio;
import com.example.demo.servicios.TematicaServicio;
import com.example.demo.utilidades.Dificultad;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@RequestMapping("/examen")
@AllArgsConstructor
public class ExamenControlador {

    private final ExamenServicio examenServicio;
    private final TematicaServicio tematicaServicio;
    private final ResultadoServicio resultadoServicio;

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView mostrarExamenesBaja() {
        ModelAndView mav = new ModelAndView("examen-administrador");
        mav.addObject("examenes", examenServicio.mostrarExamenesPorAlta(false));
        mav.addObject("titulo", "Tabla de examenes baja");
        return mav;
    }


    @GetMapping("/top20")
    public ModelAndView mostrarExamenesMasBuscados(RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("top20");
        mav.addObject("titulo", "top20");
        try {
            mav.addObject("examenes", examenServicio.mostrarExamenesMasBuscados());
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo);
        }
        return mav;
    }

    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearExamen() {
        ModelAndView mav = new ModelAndView("examen-formulario");
        mav.addObject("examen", new Examen());
        mav.addObject("dificultades", Arrays.asList(Dificultad.values()));
        mav.addObject("tematicas", tematicaServicio.mostrarTematicas());
        mav.addObject("titulo", "Crear Examen");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarExamen(@PathVariable int id, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("examen-formulario"); // Falta crear examen formulario que mande el ID

        try {
            mav.addObject("examen", examenServicio.obtenerPorId(id));
            mav.addObject("dificultades", Dificultad.values());
            mav.addObject("tematicas", tematicaServicio.mostrarTematicas());
            mav.addObject("titulo", "Editar Examen");
            mav.addObject("accion", "modificar");
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error",nulo.getMessage());
        }

        return mav;
    }

    @GetMapping("/realizar/{id}")
    public ModelAndView realizarExamen(@PathVariable int id, RedirectAttributes attributes, HttpSession session) {
        ModelAndView mav = new ModelAndView("hacer-examen");

        try {
            ExamenDTO examenDTO = examenServicio.resolverExamen(id);
            resultadoServicio.crearResultado(examenDTO, (Integer)session.getAttribute("id"));
            mav.addObject("resultado", resultadoServicio.ObtenerUltimoResultado() );
            mav.addObject("examen", examenDTO);
            mav.addObject("dificultades", Dificultad.values());
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", "No se encontro el Examen");
        }

        mav.addObject("titulo", "Realizar Examen");
        mav.addObject("accion", "modificar");
        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardar(@RequestParam Dificultad dificultad, @RequestParam Tematica tematica, @RequestParam double notaRequerida, @RequestParam String nombre, RedirectAttributes attributes) {

        try {
            examenServicio.crearExamen(dificultad,tematica,notaRequerida, nombre);
            attributes.addFlashAttribute("examen", examenServicio.ObtenerUltimoExamen());
        }catch (ObjetoNulloExcepcion nulo){
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("error", "No se encontro el Examen");
        }

            return new RedirectView("/pregunta/crear");
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar(@RequestParam int id, @RequestParam Dificultad dificultad, @RequestParam Tematica tematica, @RequestParam double notaRequerida, @RequestParam String nombre, RedirectAttributes attributes) {

        try {
            examenServicio.modificarExamen(id, dificultad, tematica, notaRequerida, nombre);
            attributes.addFlashAttribute("examen", examenServicio.obtenerPorId(id));
            return new RedirectView("/examen/{" + id + "}");
        } catch(ObjetoNulloExcepcion nulo) {
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("error", nulo.getMessage());
            return new RedirectView("/examen");
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("error", repetido.getMessage());
            System.out.println(repetido.getMessage());
            return new RedirectView("/examen");
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("error", eliminado.getMessage());
            System.out.println(eliminado.getMessage());
            return new RedirectView("/examen");
        }
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminar(@PathVariable int id, RedirectAttributes attributes) {

        try {
            examenServicio.eliminar(id);
            return new RedirectView("/tematica/" + examenServicio.obtenerPorId(id).getTematica().getId());
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/");
    }

    @PostMapping("/recuperar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView recuperar(@PathVariable int id, RedirectAttributes attributes) {

        try {
            examenServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion nulo) {
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/examen");
    }

    @GetMapping("/editarPreguntas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarPreguntas(@PathVariable int id, RedirectAttributes attributes){
        ModelAndView mav = new ModelAndView("examen-detalle");

        try{
            ExamenDTO examenDTO = examenServicio.resolverExamen(id);
            mav.addObject("examen",examenDTO);
            mav.addObject("titulo", "Detalle de " + examenDTO.getNombre() + "");
        }catch( ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return mav;
    }
}
