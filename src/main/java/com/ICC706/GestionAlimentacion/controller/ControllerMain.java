package com.ICC706.GestionAlimentacion.controller;

import com.ICC706.GestionAlimentacion.DAO.NoticiaDAO;
import com.ICC706.GestionAlimentacion.DAO.PedidoDAO;
import com.ICC706.GestionAlimentacion.DAO.UsuarioDAO;
import com.ICC706.GestionAlimentacion.model.Noticia;
import com.ICC706.GestionAlimentacion.model.Pedido;
import com.ICC706.GestionAlimentacion.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;

@Controller
@RequestMapping("/")
public class ControllerMain {
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private PedidoDAO pedidoDAO;


    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }




    @RequestMapping("/userhome")
    public String userhome(){
        return "userhome";
    }

    @RequestMapping ("/homedistribuidor")
    public String homedistribuidor(){
        return null;
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

}