package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.repository.RepoPedidos;
import Distribuidor_alimentos.service.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;


@Controller
public class ControllerPedidos {

    @Autowired
    private RepoPedidos pedidos;
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
            @RequestParam(name = "trip-start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Principal principal
    ){
        pedidos.save(new Pedido(tipo,cantidad,nota,fecha,servicioUsuarios.obtener(principal.getName())));
        return "redirect:success";
    }


    @PostMapping(value="/rectificarPedido")
    public @ResponseBody Pedido editarUsuario(Pedido pedidoAEditar){
        pedidoAEditar.setTipo(pedidoAEditar.getTipo());
        pedidoAEditar.setCantidad(pedidoAEditar.getCantidad());
        pedidoAEditar.setFecha(pedidoAEditar.getFecha());
        pedidoAEditar.setNota(pedidoAEditar.getNota());
        pedidos.save(pedidoAEditar);
        return pedidoAEditar;
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


