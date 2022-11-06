package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoPedidos extends CrudRepository<Pedido,Integer> {

    @Query(value = "SELECT * FROM Pedidos WHERE usuario_email;", nativeQuery = true)
    public List<Pedido> encontrarPorUsuario(Usuario usuarioActual);
    @Query(value = "SELECT * FROM Pedidos WHERE usuario_email ORDER BY fecha DESC LIMIT 1,1;", nativeQuery = true)
    public List<Pedido> pedidoAnterior(Usuario usuario);

}
