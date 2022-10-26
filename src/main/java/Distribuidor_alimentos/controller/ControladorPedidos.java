package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Noticia;
import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.repository.RepoPedidos;
import Distribuidor_alimentos.repository.RepoUsuarios;
import Distribuidor_alimentos.service.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Base64;


@Controller
public class ControladorPedidos {

    @Autowired
    private RepoPedidos pedidos;
    @Autowired
    private RepoUsuarios usuarios;
    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @GetMapping("/nuevoPedido")
    public String nuevoPedido(){
        return "nuevoPedido";
    }

    //creacion de pedidos
    @PostMapping("/hacerPedido")
    public String hacerPedido(
            @RequestParam(name = "tipo")String tipo,
            @RequestParam(name = "cantidad")int cantidad,
            @RequestParam(name = "nota")String nota,
            @RequestParam(name = "fecha")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Principal principal
    ){
        pedidos.save(new Pedido(tipo,cantidad,nota,fecha,servicioUsuarios.obtener(principal.getName())));
        return "redirect:success";
    }


    @GetMapping(value="/rectificarPedido")
    public String rectificarPedido(){
        return "rectificarPedido";
    }


    @PostMapping("/editarPedido")
    public String editarPedido(@RequestParam(name = "tipo")String tipo,
                               @RequestParam(name = "cantidad")int cantidad,
                               @RequestParam(name = "nota")String nota,
                               @RequestParam(name = "fecha")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                               @RequestParam(name = "id") String id,
                               Principal principal) throws IOException {
        Pedido pedido=pedidos.findById(Integer.parseInt(id)).get();
        pedido.setTipo(tipo);
        pedido.setCantidad(cantidad);
        pedido.setNota(nota);
        pedido.setFecha(fecha);
        pedidos.save(pedido);
        return "redirect:http://localhost:8080/home";
    }







    @GetMapping("/estadisticas")
    public String estadisticas(Principal principal){

        pedidos.findByUsuario(servicioUsuarios.obtener(principal.getName()));
        return "estadisticas";
    }

    /*
    @PostMapping
    public Pedido nuevoPedido(@RequestBody Pedido pedido) {
        return ControllerPedidos.guardarPedido(pedido);
    }

    private static Pedido guardarPedido(Pedido pedido) {
        return pedido;
    }

    @GetMapping
    public List<Pedido> consultarPedidos() {
        return pedidoDAO.findById();
    }

    @GetMapping("/pedido/{id}")
    public Pedido buscarPorID(@PathVariable long id) {
        return pedidoDAO.findById();
    }

    @PutMapping
    public Pedido editarPedido(@RequestBody Pedido pedido) {
        return pedidoDAO.save(pedido);
    }

    @DeleteMapping("{id}")
    public String borrarPedido(@PathVariable int id) {
        return pedidoDAO.delete();
    }

     */

}


