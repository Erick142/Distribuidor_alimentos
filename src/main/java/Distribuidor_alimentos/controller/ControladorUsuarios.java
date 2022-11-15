package Distribuidor_alimentos.controller;
import Distribuidor_alimentos.model.*;
import Distribuidor_alimentos.repository.RepoNoticias;

import Distribuidor_alimentos.repository.RepoDetallepedidos;
import Distribuidor_alimentos.repository.RepoPedido;
import Distribuidor_alimentos.repository.RepoUsuarios;
import Distribuidor_alimentos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ControladorUsuarios {
    @Autowired
    private ServicioUsuarios servicioUsuarios;
    @Autowired
    private RepoNoticias noticias;
    @Autowired
    private ServicioEnlace servicioEnlace;
    @Autowired
    private ServicioMail mailSend;

    @Qualifier("repoPedido")
    @Autowired
    private RepoPedido repoPedidos;


    @GetMapping("/")
    public String index(Model model){
        //carrusel
        model.addAttribute("noticias1",carrusel(0));
        model.addAttribute("noticias2",carrusel(1));
        model.addAttribute("noticias3",carrusel(2));
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
    public String home(Authentication authentication,Principal principal,Model model){
        //carrusel;
        model.addAttribute("noticias1",carrusel(0));
        model.addAttribute("noticias2",carrusel(1));
        model.addAttribute("noticias3",carrusel(2));
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
    @PostMapping("/registrar")
    public String registrar(@RequestParam(name = "nombre")String nombre,
                            @RequestParam(name = "email")String email,
                            @RequestParam(name = "password",defaultValue = "null")String password,
                            @RequestParam(name = "distribuidor",required = false)boolean esDistribuidor){
        servicioUsuarios.guardar(new Usuario(nombre,email,password,(esDistribuidor==true)?"distribuidor":"institucion"));
        return "redirect:confirmacion";
    }
    @PostMapping ("/invitar")
    public String invitar(@RequestParam(name = "destinatario") String emailDestinatario,Model model,Principal principal){

        Usuario usuario=servicioUsuarios.obtener(principal.getName());

        //si el distribuidor no existe
        if (servicioUsuarios.obtener(emailDestinatario)==null){
            model.addAttribute("error","la institucion ingresada no existe");
            return "redirect:home";
        }
        Usuario distribuidor=servicioUsuarios.obtener(emailDestinatario);
        //si el rol del usuario al que se le envio el enlace no es distribuidor
        if (distribuidor.getRoles().equals("institucion")){
            model.addAttribute("error","solo puede solicitar enlace con distribuidores");
            return "redirect:home";
        }
        //ya tiene enlace
        if (servicioEnlace.encontrarEnlacePorDistribuidorEInstitucion(distribuidor,usuario).isPresent()){
            return "redirect:home";
        }
        servicioEnlace.guardarEnlace(new Enlace(distribuidor,usuario,"en espera"));
        model.addAttribute("resultadoDeEnlace","Enlace solicitado satisfactoriamente");
        return "redirect:home";
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
        return "redirect:home";
    }
    @PostMapping("/eliminarenlace")
    public String eliminar(Principal principal){
            servicioEnlace.eliminarEnlace(servicioEnlace.encontrarEnlacePorInstitucion(servicioUsuarios.obtener(principal.getName())).get());
            return "redirect:home";
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
    @PostMapping("/recuperar")
    public String recuperar(@RequestParam(name = "email",defaultValue = "null") String email){
        if (!email.equals("null")){
            mailSend.enviarEmail(email);
        }
        return "redirect:/";
    }
    @GetMapping("/cambiar_contraseña")
    public String cambiarContraseña(@RequestParam(name = "token") String token,Model model){
        model.addAttribute("token",token);
        return "cambiarcontraseña";
    }
    @PostMapping("/actualizarcontraseña")
    public String actualizarContraseña(@RequestParam(name = "token") String token,
                                       @RequestParam(name = "contraseña") String password){
        Usuario usuario=servicioUsuarios.encontrarPorToken(token).get(0);
        usuario.setPassword(password);
        usuario.setTokenPassword(null);
        servicioUsuarios.guardar(usuario);
        return "redirect:login";
    }

    public List<Noticia> carrusel(int pageInt){
        Pageable page=PageRequest.of(pageInt,3);
        return noticias.findAllByOrderByIdDesc(page);
    }
}
