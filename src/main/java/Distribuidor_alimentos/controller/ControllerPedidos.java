package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.service.ServicioPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//@RestController
@RequestMapping("/pedido")
public class ControllerPedidos {

    @Autowired
    private ServicioPedidos service;

    @PostMapping
    public Pedido nuevoPedido(@RequestBody Pedido pedido) {
        return service.guardarPedido(pedido);
    }

    @GetMapping
    public List<Pedido> consultarPedidos() {
        return service.consultarPedido();
    }

    @GetMapping("{id}")
    public Pedido buscarPorID(@PathVariable int id) {
        return service.buscarPorID(id);
    }

    @PutMapping
    public Pedido editarPedido(@RequestBody Pedido pedido) {
        return service.editarPedido(pedido);
    }

    @DeleteMapping("{id}")
    public String borrarPedido(@PathVariable int id) {
        return service.borrarPedido(id);
    }
}