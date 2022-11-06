package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Enlace;
import Distribuidor_alimentos.model.Menu;
import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoEnlaces;
import Distribuidor_alimentos.repository.RepoMenus;
import Distribuidor_alimentos.repository.RepoPedidos;
import Distribuidor_alimentos.service.ServicioEnlace;
import Distribuidor_alimentos.service.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;


@Controller
public class ControladorPedidos {
    @Autowired
    private RepoPedidos repoPedidos;
    @Autowired
    private ServicioUsuarios servicioUsuarios;
    @Autowired
    private RepoMenus repoMenus;
    @Autowired
    private ServicioEnlace servicioEnlace;
    @Autowired
    private RepoEnlaces repoEnlaces;

    @GetMapping("/pedidos")
    public void listarPedidos(Model model, Principal principal) {
        Usuario usuarioActual = servicioUsuarios.obtener(principal.getName());
        model.addAttribute("pedidos",repoPedidos.encontrarPorUsuario(usuarioActual));
    }


    @GetMapping("/pedido/{id}")
    public Optional<Pedido> buscarPorID(@PathVariable int id) {
        return repoPedidos.findById(id);
    }

    @GetMapping("/nuevoPedido")
    public String nuevoPedido(Model model, Principal principal){
        listarMenus(model, principal);

        return "nuevoPedido";
    }


    List<Pedido> listaDetallepedido = new ArrayList<>();
    @GetMapping("/agregaralista")
    public String agregarAlista(
            @RequestParam(name = "tipo") String tipo,
            @RequestParam(name = "cantidad")int cantidad,
            @RequestParam(name = "nota")String nota,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model,
            Principal principal
    ){
        Pedido detallepedidoAux = new Pedido(tipo,cantidad,nota,fecha,servicioUsuarios.obtener(principal.getName()));
        listaDetallepedido.add(detallepedidoAux);
        System.out.println(detallepedidoAux);
        System.out.println(listaDetallepedido);
        model.addAttribute("pedidos", listaDetallepedido);
        //repoPedidos.save(new Pedido(tipo,cantidad,nota,fecha,servicioUsuarios.obtener(principal.getName())));
        return "redirect:/nuevoPedido";
    }

    @PostMapping("/hacerPedido")
    public String hacerPedido(
            @RequestParam(name = "tipo") String tipo,
            @RequestParam(name = "cantidad")int cantidad,
            @RequestParam(name = "nota")String nota,
            @RequestParam(name = "fecha")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model,
            Principal principal,
            Authentication authentication
    ){
        listarMenus(model,principal);
        repoPedidos.save(new Pedido(tipo,cantidad,nota,fecha,servicioUsuarios.obtener(principal.getName())));
        return "redirect:confirmacion";
    }


    @PutMapping(value="/rectificarPedido")
    public String editarPedido(@PathVariable(value = "id") int id,
                                              @RequestParam(name = "tipo") String tipo,
                                              @RequestParam(name = "cantidad") int cantidad,
                                              @RequestParam(name = "nota") String nota,
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                              Model model
    ){
        model.addAttribute("pedido",repoPedidos.findById(id));
        Pedido detallepedidoAEditar =repoPedidos.findById(id).get();
        detallepedidoAEditar.setTipo(tipo);
        detallepedidoAEditar.setCantidad(cantidad);
        detallepedidoAEditar.setNota(nota);
        detallepedidoAEditar.setFecha(fecha);
        repoPedidos.save(detallepedidoAEditar);
        return "redirect:home";
    }

    @GetMapping("/borrarpedido/{id}")
    public String borrarPedido(@PathVariable(value = "id") int id) {
        if (repoPedidos.findById(id).isPresent()
                && !repoPedidos.findById(id).get().getFecha().isBefore(LocalDate.now())){
        repoPedidos.deleteById(id);}
        else{return "redirect:error";}
        return "redirect:confirmacion";
    }

    @GetMapping("/estadisticas/{id}")
    public String verEstadicticas(@PathVariable(value = "id") int id, Model model,Principal principal) {
        Usuario usuarioActual = servicioUsuarios.obtener(principal.getName());
        model.addAttribute("pedidoMasReciente",repoPedidos.findById(id));
        model.addAttribute("pedidoAnterior", repoPedidos.pedidoAnterior(usuarioActual));
        return "estadisticas";
    }


    //Sección para añadir un tipo de menú nuevo
    @GetMapping("/vermenus")
    public String listarMenus(Model model, Principal principal) {
        try {
            Usuario usuarioActual = servicioUsuarios.obtener(principal.getName());
            if (usuarioActual.getRoles().equals("institucion")){
                Optional<Enlace> listaDistribuidores = repoEnlaces.findByInstitucion(usuarioActual);
                Usuario distribuidor=listaDistribuidores.get().getDistribuidor();
                System.out.println(distribuidor.getEmail());
                model.addAttribute("menus",repoMenus.encontrarPorUsuario(distribuidor.getEmail()));

            }else if (usuarioActual.getRoles().equals("distribuidor")){
                model.addAttribute("menus",repoMenus.encontrarPorUsuario(usuarioActual.getEmail()));
                return "agregarmenu";
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return "error";
    }

    @PostMapping("/nuevomenu")
    public String agregarMenu(
            @RequestParam(name = "nombre") String nombre,
            @RequestParam(name = "descripcion")String descripcion,
            @RequestParam(name = "icono") MultipartFile icono,
            Principal principal
    ) throws IOException {
        byte[] bytesIcono=icono.getBytes();
        String iconoB64 = Base64.getEncoder().encodeToString(bytesIcono);
            repoMenus.save(new Menu(nombre,descripcion,iconoB64,servicioUsuarios.obtener(principal.getName())));
            return "redirect:/vermenus";

    }

}


