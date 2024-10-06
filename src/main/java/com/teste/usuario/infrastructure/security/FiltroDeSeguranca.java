package com.teste.usuario.infrastructure.security;

import com.teste.usuario.infrastructure.repositories.UsuarioRepository;
import com.teste.usuario.infrastructure.repositories.entities.UsuarioEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


@Component
@RequiredArgsConstructor
public class FiltroDeSeguranca extends OncePerRequestFilter {

    private final TokenUseCase tokenUseCase;
    private final UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recuperarToken(request);
        var login = tokenUseCase.validaToken(token);

        if(login != null) {
            UsuarioEntity user = repository.findByEmail(login).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
            var autorizacoes = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(user, null, autorizacoes);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7).trim(); // Remove "Bearer " prefix e espaços adicionais
    }

}
