package PA.distribuidor.service;

import PA.distribuidor.model.Pedido;
import PA.distribuidor.repository.RepoPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPedidos {
    @Autowired
    private RepoPedidos repository;

    public Pedido guardarPedido(Pedido pedido) {
        return repository.guardar(pedido);
    }

    public List<Pedido> consultarPedido() {
        return repository.consultarPedidos();
    }

    public Pedido buscarPorID(int id) {
        return repository.buscarPorID(id);
    }

    public String borrarPedido(int id) {
        repository.borrar(id);
        return "Pedido " + id + " eliminado";
    }

    public Pedido editarPedido(Pedido pedido) {
        return repository.editar(pedido);
    }
}