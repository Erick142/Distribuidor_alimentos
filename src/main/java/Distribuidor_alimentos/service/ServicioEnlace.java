package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Enlace;
import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoEnlaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServicioEnlace {
    @Autowired
    private RepoEnlaces enlaces;

    public Optional<Enlace> encontrarPorId(Integer id){
        return enlaces.findById(id);
    }
    public List<Enlace> encontrarEnlacesPorDistribuidorYEstado(Usuario distribuidor, String estado){
        return enlaces.findAllByDistribuidorAndEstado(distribuidor,estado);
    }
    public List<Usuario> obtenerMisInstituciones(Usuario distribuidor){
        return enlaces.findAllByDistribuidorAndEstado(distribuidor,"aceptado").stream().map(enlace -> enlace.getInstitucion()).collect(Collectors.toList());
    }

    public Optional<Enlace> encontrarEnlacePorInstitucion(Usuario institucion){
        return enlaces.findByInstitucion(institucion);
    }
    public Optional<Enlace> encontrarEnlacePorDistribuidorEInstitucion(Usuario distribuidor,Usuario institucion){
        return enlaces.findByDistribuidorAndInstitucion(distribuidor,institucion);
    }
    public void eliminarEnlace(Enlace enlace){
        enlaces.delete(enlace);
    }
    public Enlace guardarEnlace(Enlace enlace){
        return enlaces.save(enlace);
    }

}
