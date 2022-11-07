package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.DetallePedido;
import Distribuidor_alimentos.model.Menu;
import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepoPedido extends CrudRepository<Pedido,Integer> {

    @Query(value = "SELECT * FROM Pedidos WHERE usuario_email=?1", nativeQuery = true)
    public List<Pedido> encontrarPorUsuario(Usuario usuarioActual);
    @Query(value = "SELECT * FROM Pedidos p WHERE p.usuario_email=?1 ORDER BY fecha DESC LIMIT 1,1", nativeQuery = true)
    public List<Pedido> pedidoAnterior(Usuario usuario);

}
