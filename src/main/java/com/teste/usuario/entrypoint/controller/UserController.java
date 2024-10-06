package com.teste.usuario.entrypoint.controller;

import com.teste.usuario.aplication.usecases.UsuarioUseCase;
import com.teste.usuario.domain.Usuario;
import com.teste.usuario.entrypoint.dto.ResponseDto;
import com.teste.usuario.entrypoint.dto.UsuarioDto;
import com.teste.usuario.entrypoint.mapper.UsuarioMapper;
import com.teste.usuario.infrastructure.security.TokenUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UserController {

    private final UsuarioUseCase useCase;
    private final TokenUseCase tokenUseCase;

    @PostMapping
    public ResponseEntity<ResponseDto<UsuarioDto>> cadastrar(@Valid @RequestBody UsuarioDto novoUsuario) {
        Usuario usuario = UsuarioMapper.paraDomain(novoUsuario);
        usuario = useCase.cadastrar(usuario);
        UsuarioDto resposta = UsuarioMapper.paraDto(usuario);
        ResponseDto<UsuarioDto> responseDto = new ResponseDto<>(resposta);
        return ResponseEntity.created(UriComponentsBuilder
                        .newInstance()
                        .path("/usuarios/{id}")
                        .buildAndExpand(resposta.getId())
                        .toUri())
                .body(responseDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> consultarPorId(@PathVariable("id") Long idUser) {
        UsuarioDto resposta = UsuarioMapper.paraDto(useCase.consultarPorId(idUser));
        ResponseDto<UsuarioDto> responseDto = new ResponseDto<>(resposta);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseDto<UsuarioDto>> alterar(@Valid @RequestBody UsuarioDto novosDados, @PathVariable("id") Long idUsuario) {
        Usuario usuario = UsuarioMapper.paraDomain(novosDados);
        usuario = useCase.alterar(usuario, idUsuario);
        UsuarioDto resposta = UsuarioMapper.paraDto(usuario);
        ResponseDto<UsuarioDto> responseDto = new ResponseDto<>(resposta);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Long idUsuario) {
        useCase.deletar(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
