package com.clean.flower.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InfoErrorException {
    @JsonProperty
    private String mensaje;
    @JsonProperty
    private int statusCode;
    @JsonProperty
    private String url;

    public InfoErrorException(int statusCode , String mensaje){
        this.statusCode = statusCode;
        this.mensaje = mensaje;
    }
}
