package Distribuidor_alimentos.controller;
import Distribuidor_alimentos.model.*;
import Distribuidor_alimentos.repository.RepoUsuarios;
import Distribuidor_alimentos.service.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class Controlador {
    @Autowired
    private ServicioUsuarios servicioUsuarios;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/")
    public String index(){
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
    public String home(Authentication authentication){
        if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("distribuidor"))){
            return "homedistribuidor";
        }
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
}
