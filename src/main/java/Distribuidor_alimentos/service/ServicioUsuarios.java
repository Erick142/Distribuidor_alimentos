package Distribuidor_alimentos.service;

import Distribuidor_alimentos.model.Usuario;
import Distribuidor_alimentos.repository.RepoUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuarios {
    @Autowired
    private RepoUsuarios repo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public void guardar(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repo.save(usuario);
    }
    public Usuario obtener(String email){
        return (repo.findById(email).isPresent())?repo.findById(email).get():null;
    }
    public List<Usuario> encontrarPorToken(String token){
        return repo.findAllByTokenPassword(token);
    }

    public RepoUsuarios getRepo() {
        return repo;
    }
}
