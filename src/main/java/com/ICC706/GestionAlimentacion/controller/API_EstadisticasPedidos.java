package com.ICC706.GestionAlimentacion.controller;
import com.ICC706.GestionAlimentacion.DAO.PedidoDAO;
import com.ICC706.GestionAlimentacion.model.Pedido;
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
    private PedidoDAO pedidoDAO;

    @GetMapping("/pedidos")
    public List<Pedido> findAll(){
        return (List<Pedido>) pedidoDAO.findAll();
    }

}