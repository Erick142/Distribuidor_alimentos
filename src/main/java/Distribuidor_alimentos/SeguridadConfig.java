package Distribuidor_alimentos;


import Distribuidor_alimentos.service.DetalleDeUsuarios;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SeguridadConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private DetalleDeUsuarios detalleDeUsuarios;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(detalleDeUsuarios);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests().antMatchers("/","/css/**","/img/**","/registro","/contraseña_olvidada","/cambiar_contraseña","/noticia/ver/**").permitAll().
                antMatchers(HttpMethod.POST,"/registrar","/recuperar","/actualizarcontraseña").permitAll().
                anyRequest().authenticated().
                and().userDetailsService(detalleDeUsuarios).
                formLogin().loginPage("/login").loginProcessingUrl("/autenticacion").successForwardUrl("/autenticacion").permitAll().
                and().logout().permitAll().
                and().csrf().disable();
    }

}
