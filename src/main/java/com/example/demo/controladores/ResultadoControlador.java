package com.example.demo.controladores;

import com.example.demo.dto.ResultadoDTO;
import com.example.demo.entidades.Resultado;
import com.example.demo.excepciones.ObjetoNulloExcepcion;
import com.example.demo.servicios.ResultadoServicio;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/resultado")
public class ResultadoControlador {

    private final ResultadoServicio resultadoServicio;

    @GetMapping("/crear")
    public ModelAndView crearResultado() {
        ModelAndView mav = new ModelAndView("");//falta crear
        mav.addObject("resultado", new Resultado());
        mav.addObject("titulo", "Crear Resultado");
        mav.addObject("accion", "guardar");
        return mav;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarResultado(@PathVariable int id, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("");

        try {
            mav.addObject("Resultado", resultadoServicio.obtenerPorId(id));
            mav.addObject("titulo", "Editar Resultado");
            mav.addObject("accion", "modificar");
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return mav;
    }

    @GetMapping("/mostrar/{id}")
    public ModelAndView mostrarResultado(@PathVariable int id, RedirectAttributes attributes) {
        ModelAndView mav = new ModelAndView("resultados-top");

        try {
            ResultadoDTO resultadoDTO = resultadoServicio.obtenerPorId(id);
            mav.addObject("Resultado", resultadoDTO);
            mav.addObject("resultado", resultadoDTO);
            mav.addObject("duracion", resultadoDTO.getDuracion() );
            mav.addObject("top", resultadoServicio.top5(resultadoDTO.getExamen().getId()));
            mav.addObject("titulo", "Editar Resultado");
            mav.addObject("accion", "modificar");
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return mav;
    }

    @PostMapping("/modificar")
    public RedirectView modificar(@RequestParam(name="resultadoId") int resultadoId, @RequestParam(name="respuestas") List<String> respuestas,@RequestParam(name="examenId") int examenId, RedirectAttributes attributes) {

        try {
            resultadoServicio.modificarResultado(resultadoId,respuestas,examenId);
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/resultado/mostrar/"+resultadoId);
    }

    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView eliminarResultado(@PathVariable int id, RedirectAttributes attributes) {
        try {
            resultadoServicio.eliminar(id);
        }catch (ObjetoNulloExcepcion nulo){
            attributes.addFlashAttribute("error", nulo.getMessage());
        }
        return new RedirectView("/resultado");
    }

    @PostMapping("/recuperar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView recuperarResultado(@PathVariable int id, RedirectAttributes attributes) {

        try {
            resultadoServicio.darAlta(id);
        } catch (ObjetoNulloExcepcion nulo) {
            attributes.addFlashAttribute("error", nulo.getMessage());
        }

        return new RedirectView("/resultado");
    }

}
