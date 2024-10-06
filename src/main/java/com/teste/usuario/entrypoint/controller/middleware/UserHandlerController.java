package com.teste.usuario.entrypoint.controller.middleware;

import com.teste.usuario.aplication.exceptions.UsuarioJaCadastradoComEmailException;
import com.teste.usuario.aplication.exceptions.UsuarioNaoEncontradoException;
import com.teste.usuario.entrypoint.dto.ResponseDto;
import com.teste.usuario.infrastructure.dataprovider.exceptions.ErroDataProviderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class UserHandlerController {

    @ExceptionHandler(UsuarioJaCadastradoComEmailException.class)
    public ResponseEntity<ResponseDto> usuarioJaCadastradoExceptionHandler(UsuarioJaCadastradoComEmailException exception) {
        ResponseDto.ErroDto  erroDto = ResponseDto.ErroDto.builder().mensagem(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDto.comErro(List.of(erroDto)));
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ResponseDto> usuarioNaoEncontradoExceptionHandler(UsuarioNaoEncontradoException exception) {
        ResponseDto.ErroDto  erroDto = ResponseDto.ErroDto.builder().mensagem(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.comErro(List.of(erroDto)));
    }


    @ExceptionHandler(ErroDataProviderException.class)
    public ResponseEntity<ResponseDto> erroDataProviderExceptionHandler(ErroDataProviderException exception) {
        ResponseDto.ErroDto  erroDto = ResponseDto.ErroDto.builder().mensagem(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.comErro(List.of(erroDto)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> erroGeral(Exception exception) {
        ResponseDto.ErroDto erroDto = ResponseDto.ErroDto.builder().mensagem(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.comErro(List.of(erroDto)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Void>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        List<FieldError> erros = exception.getFieldErrors();
        List<ResponseDto.ErroDto> erroDtos = erros.stream()
                .map(erro -> ResponseDto.ErroDto.builder()
                        .mensagem(erro.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ResponseDto<Void> responseDto = ResponseDto.comErro(erroDtos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}
