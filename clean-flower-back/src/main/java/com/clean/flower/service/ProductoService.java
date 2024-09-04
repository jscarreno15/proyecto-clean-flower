package com.clean.flower.service;

import com.clean.flower.dtos.ProductoDTO;

import java.util.List;
import java.util.Map;

public interface ProductoService {
    ProductoDTO guardarProducto(ProductoDTO producto);
    ProductoDTO editarProducto(ProductoDTO producto);
    Boolean borrarProducto(Long idProducto);
    Map<String, Object> listarProductos(Integer paginaActual, Integer totalRegistros, String descripcion, String codigo);
    ProductoDTO buscarProductoPorID(Long id);
    Boolean ventaProducto(List<ProductoDTO> listaProductos);
}
