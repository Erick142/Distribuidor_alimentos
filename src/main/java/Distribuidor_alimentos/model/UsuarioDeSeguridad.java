package Distribuidor_alimentos.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UsuarioDeSeguridad implements UserDetails {

    private Usuario usuario;
    public UsuarioDeSeguridad(Usuario usuario){
        this.usuario=usuario;
    }
    @Override
    public String getUsername() {
        //usuamos el email como nombre, porque es la clave primaria de usuario.
        return usuario.getEmail();
    }


    @Override
    public String getPassword() {
        return usuario.getPassword();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(usuario.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
