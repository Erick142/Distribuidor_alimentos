package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Enlace;
import Distribuidor_alimentos.model.Usuario;
import org.springframework.data.repository.CrudRepository;


import java.util.List;
import java.util.Optional;

public interface RepoEnlaces extends CrudRepository<Enlace,Integer> {
    Optional<Enlace> findByDistribuidorAndInstitucion(Usuario distribuidor, Usuario institucion);
    Optional<Enlace> findByInstitucion(Usuario institucion);
    List<Enlace> findAllByDistribuidorAndEstado(Usuario distribuidor, String estado);
}
