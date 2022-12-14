package Distribuidor_alimentos.controller;
import Distribuidor_alimentos.model.*;
import Distribuidor_alimentos.repository.RepoNoticias;

import Distribuidor_alimentos.service.ServicioEnlace;
import Distribuidor_alimentos.service.ServicioMail;
import Distribuidor_alimentos.service.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class Controlador {
    @Autowired
    private ServicioUsuarios servicioUsuarios;
    @Autowired
    private RepoNoticias noticias;
    @Autowired
    private ServicioEnlace servicioEnlace;
    @Autowired
    private ServicioMail mailSend;

    @GetMapping("/")
    public String index(Model model){
        if (noticias.findAllByOrderByIdDesc().size()>0){
            List<Noticia> noticias1=new ArrayList<>();
            for (int i=0;i<noticias.findAllByOrderByIdDesc().size()&&i<3;i++){
                noticias1.add(noticias.findAllByOrderByIdDesc().get(i));
            }
            model.addAttribute("noticias1",noticias1);
        }
        if (noticias.findAllByOrderByIdDesc().size()>3){
            List<Noticia> noticias2=new ArrayList<>();
            for (int i=3;i<noticias.findAllByOrderByIdDesc().size()&&i<6;i++){
                noticias2.add(noticias.findAllByOrderByIdDesc().get(i));
            }
            model.addAttribute("noticias2",noticias2);
        }
        if (noticias.findAllByOrderByIdDesc().size()>6){
            List<Noticia> noticias3=new ArrayList<>();
            for (int i=6;i<noticias.findAllByOrderByIdDesc().size()&&i<9;i++){
                noticias3.add(noticias.findAllByOrderByIdDesc().get(i));
            }
            model.addAttribute("noticias3",noticias3);
        }
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
        if (noticias.findAllByOrderByIdDesc().size()>0){
            List<Noticia> noticias1=new ArrayList<>();
            for (int i=0;i<noticias.findAllByOrderByIdDesc().size()&&i<3;i++){
                noticias1.add(noticias.findAllByOrderByIdDesc().get(i));
            }
            model.addAttribute("noticias1",noticias1);
        }
        if (noticias.findAllByOrderByIdDesc().size()>3){
            List<Noticia> noticias2=new ArrayList<>();
            for (int i=3;i<noticias.findAllByOrderByIdDesc().size()&&i<6;i++){
                noticias2.add(noticias.findAllByOrderByIdDesc().get(i));
            }
            model.addAttribute("noticias2",noticias2);
        }
        if (noticias.findAllByOrderByIdDesc().size()>6){
            List<Noticia> noticias3=new ArrayList<>();
            for (int i=6;i<noticias.findAllByOrderByIdDesc().size()&&i<9;i++){
                noticias3.add(noticias.findAllByOrderByIdDesc().get(i));
            }
            model.addAttribute("noticias3",noticias3);
        }
        model.addAttribute("username",servicioUsuarios.getRepo().findById(principal.getName()).get().getNombre());
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("distribuidor"))){
            model.addAttribute("enlaces",servicioEnlace.encontrarEnlacesPorDistribuidorYEstado(servicioUsuarios.obtener(principal.getName()),"en espera"));
            //usar mis instituciones para obtener los pedidos
            List<Usuario> misInstituciones=servicioEnlace.obtenerMisInstituciones(servicioUsuarios.obtener(principal.getName()));
            return "homedistribuidor";
        }
        model.addAttribute("enlace",servicioEnlace.encontrarEnlacePorInstitucion(servicioUsuarios.obtener(principal.getName())));
        return "userhome";
    }
    @PostMapping("/registrar")
    public String registrar(@RequestParam(name = "nombre")String nombre,
                            @RequestParam(name = "email")String email,
                            @RequestParam(name = "password",defaultValue = "null")String password,
                            @RequestParam(name = "distribuidor",required = false)boolean esDistribuidor){
        servicioUsuarios.guardar(new Usuario(nombre,email,password,(esDistribuidor==true)?"distribuidor":"institucion"));
        return "redirect:login";
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
        model.addAttribute("resultadoDeEnlaze","Enlaze solicitado satisfactoriamente");
        return "redirect:home";
    }
    @PostMapping("/resolucion")
    public String resolucion(@RequestParam(name = "seleccion") String seleccion,
                             @RequestParam(name = "id") String id,
                             Principal principal,Model model){
        Integer intId=Integer.parseInt(id);
        //si el id del enlace no existe
        if (!servicioEnlace.encontrarPorId(intId).isPresent()){
            model.addAttribute("error","ese enlace no existe");
            return "redirect:home";
        }
        //si el enlace no es del distribuidor que inicio sesion
        if (!servicioEnlace.encontrarPorId(intId).get().getDistribuidor().getEmail().equals(principal.getName())) {
            model.addAttribute("error","ese enlace no es de tu pertenencia");
            System.out.println("asdsa");
            return "redirect:home";
        }
        if ((seleccion.equals("aceptado"))) {
            Enlace enlace=servicioEnlace.encontrarPorId(intId).get();
            enlace.setEstado("aceptado");
            servicioEnlace.guardarEnlace(enlace);
        } else {
            Enlace enlace=servicioEnlace.encontrarPorId(intId).get();
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
    @GetMapping("/nuevoPedido")
    public String nuevoPedido(){
        return "nuevoPedido";
    }
    @GetMapping("/success")
    public String success(){
        return "success";
    }
    @GetMapping("/rectificarPedido")
    public String rectificarPedido(){
        return "rectificarPedido";
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
        return "success";
    }
    @GetMapping("/confirmacion")
    public String confirmacionRegistro(){
        return "ConfirmacionRegistro";
    }

    @GetMapping("/contrase??a_olvidada")
    public String contrase??aOlvidada(){
        return "recuperar";
    }
    @PostMapping("/recuperar")
    public String recuperar(@RequestParam(name = "email",defaultValue = "null") String email){
        if (!email.equals("null")){
            mailSend.enviarEmail(email);
        }
        return "redirect:/";
    }
    @GetMapping("/cambiar_contrase??a")
    public String cambiarContrase??a(@RequestParam(name = "token") String token,Model model){
        model.addAttribute("token",token);
        return "cambiarcontrase??a";
    }
    @PostMapping("/actualizarcontrase??a")
    public String actualizarContrase??a(@RequestParam(name = "token") String token,
                                       @RequestParam(name = "contrase??a") String password){
        Usuario usuario=servicioUsuarios.encontrarPorToken(token).get(0);
        usuario.setPassword(password);
        usuario.setTokenPassword(null);
        servicioUsuarios.guardar(usuario);
        return "redirect:login";
    }
}
