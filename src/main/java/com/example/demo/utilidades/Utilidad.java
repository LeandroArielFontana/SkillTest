package com.example.demo.utilidades;

import com.example.demo.excepciones.ValidacionCampExcepcion;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilidad {

    /*
    descripcion de la validacion
    El nombre de usuario debe tener :
                                    * al menos 8 caracteres y no exceder los 16 caracteres.
                                    * solo letras, números y, opcionalmente, guiones(-).
    Por ejemplo, ValidacionNombreUsuario("Juan Carlo"); devolvería false porque contiene un espacio.
    un caso favorable seria: validacionNombreUsuario("Juan-Carlo"); al cumplir con todas las condiciones devuelve true.


    public Boolean validacionNameUser(String nombreUsuario) {

        Pattern regex = Pattern.compile("/^[a-z0-9_-]{6,16}$/");
        Matcher matcher = regex.matcher(nombreUsuario);
        return matcher.matches();
    }
    */

    public static Boolean validarUsuario(String nombreUsuario) throws ValidacionCampExcepcion {
        Pattern regex = Pattern.compile("^([-_a-z-ù0-9]+)$");
        Matcher matcher = regex.matcher(nombreUsuario);
        if (!matcher.matches()) {
            throw new ValidacionCampExcepcion("El formato de usuario es inconrrecto");
        }
        return true;
    }

    /*
    descripcion de la validacion
    La validacion de cadena es valida tanto para el nombre como para el apellido
         debe tener :
                    Una letra mayuscula al comienzo,
                    No debe tener espacios
                    No debe tener caracteres especiales

    Por ejemplo, validacionCadena(""); devolvería false porque contiene un espacio.
    un caso favorable del ejemplo anterior seria: validacionNombreUsuario("Juan-Carlo"); devuelve true.
    */
    public static void validacionCadena(String cadena) throws ValidacionCampExcepcion {
        Pattern regex = Pattern.compile("^[a-zA-Z\\\\s]*$");
        Matcher matcher = regex.matcher(cadena);

        if (!matcher.matches()) {
            throw new ValidacionCampExcepcion("El formato de cadena es inconrrecto");
        }
    }

    /*
    Descripcion de la validacion
        La password de usuario debe tener :
                                            Al menos 1 mayuscula,
                                            1 minuscula,
                                            un digito,
                                            como minimo una longitud de 10 caracteres
            caso favorable, devuelve true, ejemplo: JuanCarlo0990
            caso desfavorable, devuelve false, ejemplo:juan09carlo, no contiene mayusculas
*/
    public static Boolean validacionPassword(String password)throws ValidacionCampExcepcion{
        Pattern regex = Pattern.compile("^(?=.{10,}$)(?=(?:.*[A-Z]){2})(?=.*[a-z])(?=(?:.*[0-9]){2}).*");
        Matcher matcher = regex.matcher(password);
        if (!matcher.matches()) {
            throw new ValidacionCampExcepcion("El formato de Password es inconrrecto");
        }
        return true;
    }

    /*
descripcion de la validacion
El email puede:
              iniciar con caracteres(letras) pueden ser mayusculas o minusculas, combinado con digitos del 0 al 9 y -
              puede contener de forma opcional un punto seguido de uno o más de los caracteres.
              seguido de esto debe contener el carácter @
              Después de la @ el email debe contener otra combinacion de caracteres.
              luego un punto
              Seguido de un punto finaliza con al menos 2 de caracteres (final del email)
    caso favorable, devuelve true. ejemplo: juan90carlo@gmail.com
    caso desfavorable, devuelve false.ejemplo: juan90carlo.gmail.com

*/
   public static Boolean validacionMail(String mail)throws ValidacionCampExcepcion{
        Pattern regex = Pattern.compile("^[\\\\w-]+(\\\\.[\\\\w-]+)*@[A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$");
        Matcher matcher = regex.matcher(mail);
        if (!matcher.matches()) {
            throw new ValidacionCampExcepcion("El formato de mail es inconrrecto");
        }
        return true;
    }


    /*
descripcion de la validacion
La Edad del usuario debe ser :
                                de 18 en adelante. ?

   caso favorable, devuelve true, ejemplo: 28
   caso desfavorable, devuelve false, ejemplo: 9
*/
    public static Boolean validacionEdad(String edad)throws ValidacionCampExcepcion{
        Pattern regex = Pattern.compile("^(0?[1-9]|[1-9][0-9])$\n");
        Matcher matcher = regex.matcher(edad);
        if (!matcher.matches()) {
            throw new ValidacionCampExcepcion("El formato de edad es inconrrecto");
        }
        return true;
    }

    /*
    descripcion de la validacion
    El numero de telefono debe tener:
                                     10 caracteres,numericos.?
    */
    public static Boolean validacionTelefono(String telefono)throws ValidacionCampExcepcion{
        Pattern regex = Pattern.compile("[1-9]\\\\d{8}");
        Matcher matcher = regex.matcher(telefono);
        if (!matcher.matches()) {
            throw new ValidacionCampExcepcion("El formato de telefono es inconrrecto");
        }
        return true;
    }

    public static String generadorDeCadenas(){
        String caracteres = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
        Random random = new Random();
        char[] cadena = new char[8];
        for (int i = 0; i < 8; i++){
            cadena[i] = caracteres.charAt(random.nextInt(caracteres.length()));
        }
        return new String(cadena);
    }

}



