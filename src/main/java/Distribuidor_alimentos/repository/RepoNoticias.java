package Distribuidor_alimentos.repository;

import Distribuidor_alimentos.model.Noticia;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RepoNoticias extends PagingAndSortingRepository<Noticia,Integer> {

    List<Noticia> findAllByOrderByIdDesc(Pageable pageable);


}
