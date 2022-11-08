package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.DetallePedido;
import Distribuidor_alimentos.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoDetallepedidos extends CrudRepository<DetallePedido,Integer> {

    @Query(value = "SELECT * FROM detalle_pedidos dp WHERE dp.usuario_email=?1", nativeQuery = true)
    public List<DetallePedido> encontrarPorUsuario(Usuario usuarioActual);


    @Query(value = "SELECT * FROM detalle_pedidos dp WHERE dp.pedido_id=?1", nativeQuery = true)
    public List<DetallePedido> obtenerDetalle(int id);

    @Query(value = "SELECT cantidad,tipo FROM detalle_pedidos dp WHERE dp.pedido_id=?1 ORDER BY id DESC LIMIT 1,1", nativeQuery = true)
    public List<String> obtenerDetalleActual(int pedido_id);

    @Query(value = "SELECT cantidad,tipo FROM detalle_pedidos dp WHERE dp.pedido_id=?1 ORDER BY id DESC LIMIT 2,2", nativeQuery = true)
    public List<String> obtenerDetalleAnterior(int pedido_id);


}
