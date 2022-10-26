package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.repository.RepoPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class API_EstadisticasPedidos {

    //API para llenar los gráficos
    @Autowired
    private RepoPedidos repoPedidos;

    @GetMapping("/pedidos")
    public List<Pedido> findAll(){
        return (List<Pedido>) repoPedidos.findAll();
    }

    @GetMapping("/pedidos/{id}")
    public Optional<Pedido> porID(@PathVariable int id){
        return repoPedidos.findById(id);
    }

}