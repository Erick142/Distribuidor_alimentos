package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Noticia;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RepoNoticias extends CrudRepository<Noticia,Integer> {

    List<Noticia> findAllByOrderByIdDesc();


}
