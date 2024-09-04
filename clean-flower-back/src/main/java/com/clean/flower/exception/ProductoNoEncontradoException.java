package com.clean.flower.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductoNoEncontradoException extends  RuntimeException{
    private final HttpStatus status;
    private final String mensaje;

    public ProductoNoEncontradoException(HttpStatus status, String mensaje){
        super(mensaje);
        this.status= status;
        this.mensaje = mensaje;
    }
}
