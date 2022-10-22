package Distribuidor_alimentos.repository;
import Distribuidor_alimentos.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Repository
public class RepoPedidos {
    private List<Pedido> listaPedidos = new ArrayList<Pedido>();

    public void nuevoPedido() {
        listaPedidos = List.of(
                /*
                new Pedido(1,"Estándar",120,"Esta es una nota de prueba 1","04/10/2022"),
                new Pedido(2, "Hipocalórico",120,"Esta es una nota de prueba 2","02/07/2022"),
                new Pedido(3, "Vegetariano",120,"Esta es una nota de prueba 3","03/08/2022")
                 */
        );
    }

    public List<Pedido> consultarPedidos() {
        return listaPedidos;
    }

    public Pedido buscarPorID(int id){
        for (int i = 0; i < listaPedidos.size(); i++) {
            if (listaPedidos.get(i).getId() == (id)) {
                return listaPedidos.get(i);
            }
        }
        return null;
    }

    public List<Pedido> buscarPorTipo(String tipo) {
        return listaPedidos.stream().filter(x -> x.getTipo().startsWith(tipo)).collect(Collectors.toList());
    }

    public Pedido guardar(Pedido p) {
        Pedido pedido = new Pedido();
        pedido.setId(p.getId());
        pedido.setTipo(p.getTipo());
        pedido.setCantidad(p.getCantidad());
        pedido.setNota(p.getNota());
        pedido.setFecha(p.getFecha());
        listaPedidos.add(pedido);
        return pedido;
    }

    public String borrar(Integer id) {
        listaPedidos.removeIf(x -> x.getId() == (id));
        return null;
    }

    public Pedido editar(Pedido pedido) {
        int idx = 0;
        int id = 0;
        for (int i = 0; i < listaPedidos.size(); i++) {
            if (listaPedidos.get(i).getId() == (pedido.getId())) {
                id = pedido.getId();
                idx = i;
                break;
            }
        }

        Pedido pedido1 = new Pedido();
        pedido1.setId(id);
        pedido1.setTipo(pedido.getTipo());
        pedido1.setCantidad(pedido.getCantidad());
        pedido1.setNota(pedido.getNota());
        pedido1.setFecha(pedido.getFecha());
        listaPedidos.set(idx, pedido);
        return pedido1;
    }
}