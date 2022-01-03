package com.example.demo.controladores;

import com.example.demo.dto.PreguntaDTO;
import com.example.demo.entidades.Examen;
import com.example.demo.entidades.Pregunta;
import com.example.demo.excepciones.ObjetoEliminadoExcepcion;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.excepciones.ObjetoRepetidoExcepcion;
import com.example.demo.excepciones.PadreNuloExcepcion;
import com.example.demo.servicios.ExamenServicio;
import com.example.demo.servicios.PreguntaServicio;
import com.example.demo.utilidades.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/pregunta")
public class PreguntaControlador{

    private final PreguntaServicio preguntaServicio;
    private final ExamenServicio examenServicio;

    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView crearPregunta(RedirectAttributes attributes, @RequestParam(name = "examenId",required = false) Integer examenId) {
        ModelAndView mav = new ModelAndView("pregunta-formulario");


        try{
            if(examenId != null){
                System.out.println(examenServicio.obtenerPorId(examenId).toString());
                mav.addObject("examen" , examenServicio.obtenerPorId(examenId));
            }else{
                System.out.println(examenServicio.ObtenerUltimoExamen());
                mav.addObject("examen" , examenServicio.ObtenerUltimoExamen());
            }
            mav.addObject("pregunta", new Pregunta());
            mav.addObject("titulo", "Crear Pregunta");
            mav.addObject("accion", "guardar");
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
            mav.setViewName("redirect:/examen");
        }

        return mav;
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView editarPregunta(@PathVariable int id, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("pregunta-editar");

        try {
            PreguntaDTO preguntaDTO = preguntaServicio.obtenerPorId(id);
            mav.addObject("examen", preguntaDTO.getExamen());
            mav.addObject("pregunta", Mapper.preguntaDTOAPreguntaEditableDTO(preguntaDTO));
            mav.addObject("titulo", "Editar Pregunta");
            mav.addObject("accion", "modificar");
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
            mav.setViewName("redirect:/examen");
        }

        return mav;
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView guardarPregunta(@RequestParam String enunciado, @RequestParam(value="accion") String accion, @RequestParam(value="respuesta2") String respuesta2, @RequestParam(value="respuesta3") String respuesta3,@RequestParam(value="respuesta4") String respuesta4, @RequestParam String respuestaCorrecta, @RequestParam int puntaje, @RequestParam(value="examen") Integer examenId,@RequestParam(value="tematica") Integer tematicaId,  HttpServletRequest request, RedirectAttributes attributes) {

        try {
            preguntaServicio.crearPregunta(enunciado, respuesta2, respuesta3, respuesta4, respuestaCorrecta, puntaje, examenId);
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("error", repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("error", eliminado.getMessage());
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }catch (PadreNuloExcepcion padre){
            attributes.addFlashAttribute("error", padre.getMessage());
            return new RedirectView("/examen/editarPreguntas/"+examenId);
        }

        if (accion.equalsIgnoreCase("siguientePregunta")){
            return new RedirectView("/pregunta/crear");
        }else{
            return new RedirectView("/examen/editarPreguntas/"+examenId);
        }
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView modificar(@RequestParam String enunciado,@RequestParam String respuesta2, @RequestParam String respuesta3,@RequestParam String respuesta4, @RequestParam String respuestaCorrecta, @RequestParam int puntaje, @RequestParam Examen examen, @RequestParam int id, RedirectAttributes attributes) {

        try{
            preguntaServicio.modificarPregunta(enunciado, respuesta2, respuesta3, respuesta4,respuestaCorrecta, puntaje, examen, id);
        }catch(ObjetoNulloExcepcion nulo) {
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("error", nulo.getMessage());
        }catch (ObjetoRepetidoExcepcion repetido){
            attributes.addFlashAttribute("error", repetido.getMessage());
            System.out.println(repetido.getMessage());
        }catch (ObjetoEliminadoExcepcion eliminado){
            attributes.addFlashAttribute("error", eliminado.getMessage());
            System.out.println(eliminado.getMessage());
        }

        return new RedirectView("/examen/editarPreguntas/"+ examen.getId());
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminarPregunta(@PathVariable int id, RedirectAttributes attributes) {

        try{
            preguntaServicio.eliminar(id);
            return new RedirectView("/examen/editarPreguntas/" + preguntaServicio.obtenerPorId(id).getExamen().getId());
        }catch (ObjetoNulloExcepcion nulo){
            System.out.println(nulo.getMessage());
            attributes.addFlashAttribute("error", nulo.getMessage());
            return new RedirectView("/");
        }

    }
}