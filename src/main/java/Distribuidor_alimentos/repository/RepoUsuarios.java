package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoUsuarios extends CrudRepository<Usuario,String> {
}