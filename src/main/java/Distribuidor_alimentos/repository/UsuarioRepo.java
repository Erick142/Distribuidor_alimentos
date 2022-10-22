package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepo extends CrudRepository<Usuario,Long> {
}