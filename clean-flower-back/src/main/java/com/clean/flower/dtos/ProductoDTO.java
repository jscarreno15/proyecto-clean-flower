package com.clean.flower.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoDTO {
    private Long id;
    @NotNull(message = "producto.valid.descripcion")
    @NotBlank(message = "producto.valid.descripcion")
    private String descripcion;
    @NotNull(message = "producto.valid.codigo")
    @NotBlank(message = "producto.valid.codigo")
    private String codigo;
    @NotNull(message = "producto.valid.cantidad")
    private Integer cantidad;
    @NotNull(message = "producto.valid.precioCosto")
    private Integer precioCosto;
    @NotNull(message = "producto.valid.precioVenta")
    private Integer precioVenta;

}
