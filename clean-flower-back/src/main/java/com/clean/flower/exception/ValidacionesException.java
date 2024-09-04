package com.clean.flower.exception;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidacionesException extends Throwable {
    @JsonProperty
    Map<String, String> errores = new HashMap<>();

    @JsonProperty
    private int codigoError;
}
