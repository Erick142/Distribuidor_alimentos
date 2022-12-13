package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Pedido;
import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoPedido;
import Distribuidor_alimentos.repository.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPedidos  {
    @Autowired
    private RepoPedido repoPedido;
    @Autowired
    private RepoUsuarios repo;

}
