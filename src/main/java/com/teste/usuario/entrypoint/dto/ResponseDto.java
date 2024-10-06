package com.teste.usuario.entrypoint.dto;

import com.teste.usuario.infrastructure.dataprovider.exceptions.ErroDataProviderException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseDto<T> {

    private T dado;
    private ErroDto erro;

    public ResponseDto(T dado) {
        this.dado = dado;
    }

    public static ResponseDto comErro(ErroDto erro) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.erro = erro;
        return responseDto;
    }

    @Builder
    public static class ErroDto {
        String mensagem;
    }

}
