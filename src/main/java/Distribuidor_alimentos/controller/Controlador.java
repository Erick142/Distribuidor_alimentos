package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoPedido;
import Distribuidor_alimentos.service.ServicioEnlace;
import Distribuidor_alimentos.service.ServicioNoticias;
import Distribuidor_alimentos.service.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class Controlador {
    @Autowired
    private ServicioNoticias servicioNoticias;
    @Autowired
    private ServicioUsuarios servicioUsuarios;
    @Autowired
    private ServicioEnlace servicioEnlace;

    @Qualifier("repoPedido")
    @Autowired
    private RepoPedido repoPedidos;

    @GetMapping("/")
    public String index(Model model){
        //carrusel
        model.addAttribute("noticias1",servicioNoticias.carrusel(0));
        model.addAttribute("noticias2",servicioNoticias.carrusel(1));
        model.addAttribute("noticias3",servicioNoticias.carrusel(2));
        //end carrusel
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registro")
    public String registro(){
        return "registro";
    }

    @PostMapping("/autenticacion")
    public String autenticacion(Authentication authentication){
        //por alguna razon la Autentication aqui era null, por eso la autenticacion se ralizara en home().
        return "redirect:home";
    }
    @GetMapping("/home")
    public String home(Authentication authentication, Principal principal, Model model){
        //carrusel;
        model.addAttribute("noticias1",servicioNoticias.carrusel(0));
        model.addAttribute("noticias2",servicioNoticias.carrusel(1));
        model.addAttribute("noticias3",servicioNoticias.carrusel(2));
        //end carrusel

        if (!servicioUsuarios.existePorEmail(principal.getName())){
            return "redirect:/login";
        }
        Usuario usuarioLogeado=servicioUsuarios.obtener(principal.getName());
        model.addAttribute("username",usuarioLogeado.getNombre());
        // si tiene el rol distribuidor
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("distribuidor"))){
            model.addAttribute("enlaces",servicioEnlace.encontrarEnlacesPorDistribuidorYEstado(usuarioLogeado,"en espera"));
            //encontrar pedidos de las instituciones.
            List<Pedido> pedidosDeMisInstituciones=servicioUsuarios.encontrarLosPedidosDeMisInstituciones(usuarioLogeado);
            // Collections.sort(pedidosins);
            model.addAttribute("pedidos",pedidosDeMisInstituciones);
            return "homedistribuidor";
        }
        model.addAttribute("enlace",servicioEnlace.encontrarEnlacePorInstitucion(usuarioLogeado));
        model.addAttribute("pedidos", repoPedidos.encontrarPorUsuario(usuarioLogeado));
        return "userhome";
    }
    @GetMapping("/success")
    public String success(){
        return "confirmacion";
    }

    @GetMapping("/estadisticas")
    public String estadisticas(){
        return "estadisticas";
    }
    @GetMapping("/error")
    public String error(){
        return "error";
    }
    @GetMapping("/exito")
    public String exito(){
        return "confirmacion";
    }
    @GetMapping("/confirmacion")
    public String confirmacionRegistro(){
        return "ConfirmacionRegistro";
    }

    @GetMapping("/contraseña_olvidada")
    public String contraseñaOlvidada(){
        return "recuperar";
    }
}
