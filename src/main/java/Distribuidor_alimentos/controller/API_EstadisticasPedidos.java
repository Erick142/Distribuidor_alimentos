package Distribuidor_alimentos.controller;

import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoDetallepedidos;
import Distribuidor_alimentos.repository.RepoPedido;
import Distribuidor_alimentos.service.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class API_EstadisticasPedidos {

    //para llenar los gráficos
    @Autowired
    private RepoDetallepedidos repoDetallepedidos;
    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @Autowired
    private RepoPedido repoPedido;


    @GetMapping("/api/pedidoanterior")
    public List<String> statsPedidoAnterior(Principal principal){
        List<String> detallesAnterior=repoDetallepedidos.obtenerDetalleActual(encontrarIDActual(principal));
        return detallesAnterior;
    }

    @GetMapping("/api/pedidoactual")
    public List<String> statsPedidoActual(Principal principal){
        List<String> detallesAnterior=repoDetallepedidos.obtenerDetalleAnterior(encontrarIDActual(principal));
        return detallesAnterior;
    }


    public int encontrarIDActual(Principal principal){
        Usuario actual = obtenerUsuarioActual(principal);
        int idActual= repoPedido.pedidoActual(actual).get(0).getId();
        return (idActual) ;
    }

    public int encontrarIDAnterior(Principal principal){
        Usuario actual = obtenerUsuarioActual(principal);
        int idAnteior= repoPedido.idAnterior(actual);
        return (idAnteior) ;
    }

    public Usuario obtenerUsuarioActual(Principal principal){
        Usuario usuarioActual = servicioUsuarios.obtener(principal.getName());
        return usuarioActual;
    }
}