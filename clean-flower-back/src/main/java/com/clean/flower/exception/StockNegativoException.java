package com.clean.flower.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StockNegativoException  extends  RuntimeException{
    private final HttpStatus status;
    private final String mensaje;

    public StockNegativoException(HttpStatus status, String mensaje){
        super(mensaje);
        this.status= status;
        this.mensaje = mensaje;
    }
}
