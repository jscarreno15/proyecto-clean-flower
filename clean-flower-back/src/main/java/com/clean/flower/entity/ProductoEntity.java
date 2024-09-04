package com.clean.flower.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.util.Date;

@Entity
@Table(name = "PRODUCTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CODIGO")
    private String codigo;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Column(name = "PRECIO_COSTO")
    private Integer precioCosto;
    @Column(name = "PRECIO_VENTA")
    private Integer precioVenta;
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    @Column(name = "ULTIMA_MODIFICACION")
    private Date ultimaModificacion;
}
