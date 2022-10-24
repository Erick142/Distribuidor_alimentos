package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Noticia;
import org.springframework.data.repository.CrudRepository;

public interface RepoNoticias extends CrudRepository<Noticia,Integer> {
}
