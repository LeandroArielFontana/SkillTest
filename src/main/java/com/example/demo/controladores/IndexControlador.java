package com.example.demo.controladores;

import com.example.demo.dto.CategoriaDTO;
import com.example.demo.servicios.CategoriaServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
public class IndexControlador {

    private final CategoriaServicio categoriaServicio;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("categorias", categoriaServicio.mostrarCategoriasPorAlta(true));
        mav.addObject("titulo", "Inicio");
        return mav;
    }


}
