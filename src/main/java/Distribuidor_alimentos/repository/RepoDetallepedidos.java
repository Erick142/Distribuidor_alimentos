package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.DetallePedido;
import Distribuidor_alimentos.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoDetallepedidos extends CrudRepository<DetallePedido,Integer> {
    public List<DetallePedido> findAllByUsuario(Usuario usuario);

    /*
    @Query(value = "SELECT * FROM Pedidos WHERE usuario_email ORDER BY fecha DESC LIMIT 1,1;", nativeQuery = true)
    public List<DetallePedido> pedidoAnterior(Usuario usuario);
     */

}
