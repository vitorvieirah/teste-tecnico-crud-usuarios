package com.teste.usuario.entrypoint.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseDto<T> {

    @JsonProperty("dado")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T dado;

    @JsonProperty("erro")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErroDto erro;

    public ResponseDto(T dado) {
        this.dado = dado;
    }

    public static <T> ResponseDto<T> comErro(ErroDto erro) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setErro(erro);
        return responseDto;
    }

    @Getter
    @Setter
    @Builder
    public static class ErroDto {
        private String mensagem;
    }
}

