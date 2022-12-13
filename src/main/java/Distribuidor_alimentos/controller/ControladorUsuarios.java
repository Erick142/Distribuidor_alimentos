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

@RequestMapping("/usuario")
@Controller
public class ControladorUsuarios {
    @Autowired
    private ServicioUsuarios servicioUsuarios;
    @Autowired
    private ServicioMail mailSend;
    @PostMapping("/registrar")
    public String registrar(@RequestParam(name = "nombre")String nombre,
                            @RequestParam(name = "email")String email,
                            @RequestParam(name = "password",defaultValue = "null")String password,
                            @RequestParam(name = "distribuidor",required = false)boolean esDistribuidor){
        servicioUsuarios.guardar(new Usuario(nombre,email,password,(esDistribuidor==true)?"distribuidor":"institucion"));
        return "redirect:../confirmacion";
    }
    @PostMapping("/recuperar")
    public String recuperar(@RequestParam(name = "email",defaultValue = "null") String email){
        if (!email.equals("null")){
            mailSend.enviarEmail(email);
        }
        return "redirect:../";
    }
    @GetMapping("/cambiar_contraseña")
    public String cambiarContraseña(@RequestParam(name = "token") String token,Model model){
        model.addAttribute("token",token);
        return "cambiarcontraseña";
    }
    @PostMapping("/actualizarcontraseña")
    public String actualizarContraseña(@RequestParam(name = "token") String token,
                                       @RequestParam(name = "contraseña") String password){
        Usuario usuario=servicioUsuarios.encontrarPorToken(token).get();
        usuario.setPassword(password);
        usuario.setTokenPassword(null);
        servicioUsuarios.guardar(usuario);
        return "redirect:login";
    }


}
