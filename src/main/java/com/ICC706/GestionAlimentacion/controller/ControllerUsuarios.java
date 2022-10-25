package com.ICC706.GestionAlimentacion.controller;

import com.ICC706.GestionAlimentacion.DAO.UsuarioDAO;
import com.ICC706.GestionAlimentacion.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ControllerUsuarios {
    private UsuarioDAO usuarioDAO;

    @GetMapping("/registro")
    public String registro(){
        return "registro";
    }

    @GetMapping("/confirmacion")
    public String confirmacionRegistro(){
        return "confirmacionRegistro";
    }

    //creacion de usuarios
    @PostMapping("/registrar")
    public String registrar(
            @RequestParam(name = "nombre")String nombre,
            @RequestParam(name = "aPaterno")String aPaterno,
            @RequestParam(name = "aMaterno")String aMaterno,
            @RequestParam(name = "nombreInstitucion")String nombreInstitucion,
            @RequestParam(name = "email")String email,
            @RequestParam(name = "password",required = true)String password,
            @RequestParam(name = "distribuidor",required = false)boolean esDistribuidor
            /*
            ,
            @RequestParam(name = "direccion")String direccion,
            @RequestParam(name = "region")String region,
            @RequestParam(name = "comuna")String comuna,
            @RequestParam(name = "ciudad")String ciudad
             */
    ){
        usuarioDAO.save(new Usuario(nombre,aPaterno,aMaterno,nombreInstitucion,email,password,(esDistribuidor==true)?"distribuidor":"institucion"));
        return "redirect:confirmacion";
    }



    /*
        @RequestMapping(value="/editarUsuario", method = RequestMethod.POST)
        public @ResponseBody Usuario editarUsuario(Usuario usuarioAEditar){
            usuarioAEditar.setNombre(usuarioAEditar.getNombre());
            usuarioAEditar.setAMaterno(usuarioAEditar.getAMaterno());
            usuarioAEditar.setAPaterno(usuarioAEditar.getAPaterno());
            usuarioAEditar.setCiudad(usuarioAEditar.getCiudad());
            usuarioAEditar.setEmail(usuarioAEditar.getEmail());
            usuarioAEditar.setPassword(usuarioAEditar.getPassword());
            usuarioDao.save(usuarioAEditar);
            return usuarioAEditar;
        }

        @RequestMapping(value="/eliminarUsuario", method = RequestMethod.DELETE)
        public @ResponseBody Usuario eliminarUsuario(Usuario usuarioAEliminar){
            usuarioDao.delete(usuarioAEliminar);
            return usuarioAEliminar;
        }

        @RequestMapping(value="/validar" ,method = RequestMethod.POST)
        public  @ResponseBody Usuario loginUsuario(@RequestBody Usuario usuarioPendiente){
            Usuario usuarioLogeado = null;
            List <Usuario> usuarios = (List<Usuario>) usuarioDao.findAll();
            usuarioLogeado = comprobarUsuario(usuarios, usuarioPendiente.getEmail(),usuarioPendiente.getPassword());
            return usuarioLogeado;
        }

        @RequestMapping(value="/verPerfilUsuario", method = RequestMethod.GET)
        public @ResponseBody Usuario verPerfilUsuario(Usuario usuarioAMostrar){
            usuarioAMostrar.getNombre();
            usuarioAMostrar.getAPaterno();
            usuarioAMostrar.getAMaterno();
            usuarioAMostrar.getCiudad();
            usuarioAMostrar.getEmail();
            usuarioAMostrar.getPassword();
            return usuarioAMostrar;
        }

        /*
        @RequestMapping(value="/loginAdministrador" ,method = RequestMethod.POST)
        public  @ResponseBody Administrador loginAdministrador(@RequestBody Administrador administradorPendiente){
            Administrador administradorLogeado = null;
            List <Administrador> administradores = administradorDao.findAll();
            administradorLogeado = comprobarAdministrador(administradores, administradorPendiente.getEmail(),administradorPendiente.getPassword());
            return administradorLogeado;
        }


        private Usuario comprobarUsuario(List<Usuario> usuarios, String email, String password) {
            Usuario u = null;
            for (Usuario usuario : usuarios) {
                if (email.equals(usuario.getEmail()) && password.equals(usuario.getPassword())){
                    u = usuario;
                    break;
                }
            }
            return u;
        }

        /*
        private Administrador comprobarAdministrador( List<Administrador> administradores, String email, String password) {
            Administrador ad = null;
            for (Administrador administrador : administradores) {
                if (email.equals(administrador.getEmail()) && password.equals(administrador.getPassword())){
                    ad = administrador;
                    //break;
                }
            }
            return ad;
        }
         */

    }


