package com.teste.usuario.infrastructure.security;

import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class DetalhesUsuarioPersonalizadoService implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity user = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getSenha(), new ArrayList<>());
    }
}
