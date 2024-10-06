package com.teste.usuario.infrastructure.dataprovider.exceptions;

public class ErroDataProviderException extends RuntimeException {
    public ErroDataProviderException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
