package com.clean.flower.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductoMismoCodigoException extends  RuntimeException{
    private final HttpStatus status;
    private final String mensaje;

    public ProductoMismoCodigoException(HttpStatus status, String mensaje){
        super(mensaje);
        this.status= status;
        this.mensaje = mensaje;
    }
}
