/*
package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.repository.RepoPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
 */