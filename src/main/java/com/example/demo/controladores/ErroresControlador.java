package com.example.demo.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class ErroresControlador implements ErrorController {

    @RequestMapping (value = "/error", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView errores(HttpServletResponse response){
        ModelAndView mav= new ModelAndView("errores");
        String mensaje="";
        int codigo= response.getStatus();


        //Hay que corregir los mensajes de errores

        switch (codigo){
            case 400:
                mensaje ="Error la consulta ingresada no es valida";
                break;
            case 403:
                mensaje="No posee accesos";
                break;
            case 404:
                mensaje="No se encontro la ruta";
                break;
            case 500:
                mensaje="Error no sos vos soy yo";
                break;
            default:
                mensaje="Elol";
        }
        mav.addObject("codigo", codigo);
        mav.addObject("mensaje", mensaje );
        return mav;

    }

}
