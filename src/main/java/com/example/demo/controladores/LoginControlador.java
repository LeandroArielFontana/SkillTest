package com.example.demo.controladores;

import com.example.demo.servicios.UsuarioServicio;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class LoginControlador {

    private final UsuarioServicio usuarioServicio;

    @GetMapping("/login")
    public ModelAndView login (@RequestParam(required = false) String error){
        ModelAndView mav = new ModelAndView("login");
        if (error != null){
            mav.addObject("error", error);
        }
        return mav;
    }
}