package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Enlace;
import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.service.ServicioEnlace;
import Distribuidor_alimentos.service.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/enlace")
@Controller
public class ControladorEnlace {
    @Autowired
    private ServicioEnlace servicioEnlace;
    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @PostMapping("/invitar")
    public String invitar(@RequestParam(name = "destinatario") String emailDestinatario, Model model, Principal principal){

        Usuario usuario=servicioUsuarios.obtener(principal.getName());

        //si el distribuidor no existe
        if (servicioUsuarios.obtener(emailDestinatario)==null){
            model.addAttribute("error","la institucion ingresada no existe");
            return "redirect:../home";
        }
        Usuario distribuidor=servicioUsuarios.obtener(emailDestinatario);
        //si el rol del usuario al que se le envio el enlace no es distribuidor
        if (distribuidor.getRoles().equals("institucion")){
            model.addAttribute("error","solo puede solicitar enlace con distribuidores");
            return "redirect:../home";
        }
        //ya tiene enlace
        if (servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,usuario).isPresent()){
            return "redirect:../home";
        }
        servicioEnlace.guardarEnlace(new Enlace(distribuidor,usuario,"en espera"));
        model.addAttribute("resultadoDeEnlace","Enlace solicitado satisfactoriamente");
        return "redirect:../home";
    }
    @PostMapping("/resolucion")
    public String resolucion(@RequestParam(name = "seleccion") String seleccion,
                             @RequestParam(name = "id") Integer id,
                             Principal principal,Model model){
        //si el id del enlace no existe
        if (!servicioEnlace.encontrarPorId(id).isPresent()){
            model.addAttribute("error","ese enlace no existe");
            return "redirect:home";
        }
        //si el enlace no es del distribuidor que inicio sesion
        if (!servicioEnlace.encontrarPorId(id).get().getDistribuidor().getEmail().equals(principal.getName())) {
            model.addAttribute("error","ese enlace no es de tu pertenencia");
            System.out.println("asdsa");
            return "redirect:home";
        }
        if ((seleccion.equals("aceptado"))) {
            Enlace enlace=servicioEnlace.encontrarPorId(id).get();
            enlace.setEstado("aceptado");
            servicioEnlace.guardarEnlace(enlace);;
        } else {
            Enlace enlace=servicioEnlace.encontrarPorId(id).get();
            enlace.setEstado("rechazado");
            servicioEnlace.guardarEnlace(enlace);
        }
        return "redirect:../home";
    }
    @PostMapping("/eliminarenlace")
    public String eliminar(Principal principal){
        servicioEnlace.eliminarEnlace(servicioEnlace.encontrarEnlacePorInstitucion(servicioUsuarios.obtener(principal.getName())).get());
        return "redirect:../home";
    }
}
