package com.clean.flower.service.impl;

import com.clean.flower.entity.ProductoEntity;
import com.clean.flower.exception.ProductoMismoCodigoException;
import com.clean.flower.exception.StockNegativoException;
import com.clean.flower.util.Util;
import com.clean.flower.dtos.ProductoDTO;
import com.clean.flower.exception.ProductoNoEncontradoException;
import com.clean.flower.repository.ProductoRepository;
import com.clean.flower.service.ProductoService;
import com.clean.flower.specification.ProductoSpecification;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductoSpecification productoSpecification;

    @Override

    public ProductoDTO guardarProducto(ProductoDTO productoDTO) {
        log.info(":::::::: SERVICIO - GUARDAR PRODUCTO ::::::::");
        if(productoRepository.findByCodigo(productoDTO.getCodigo()).isPresent())
            throw new ProductoMismoCodigoException(HttpStatus.BAD_REQUEST, productoDTO.getCodigo());
        ProductoEntity productoEntity = modelMapper.map(productoDTO, ProductoEntity.class);
        productoEntity.setFechaCreacion(new Date());
        productoEntity.setUltimaModificacion(new Date());
        productoEntity = productoRepository.save(productoEntity);
        return modelMapper.map(productoEntity, ProductoDTO.class);
    }

    @Override
    public ProductoDTO editarProducto(ProductoDTO productoDTO) {
        log.info(":::::::: SERVICIO - EDITAR PRODUCTO ::::::::");
        Optional.ofNullable(productoDTO.getId()).orElseThrow(() -> new RuntimeException("Campo ID requerido"));
        ProductoEntity productoEntity = modelMapper.map(productoDTO, ProductoEntity.class);
        productoEntity.setUltimaModificacion(new Date());
        productoEntity = productoRepository.save(productoEntity);
        return modelMapper.map(productoEntity, ProductoDTO.class);
    }

    @Override
    public Boolean borrarProducto(Long idProducto) {
        log.info(":::::::: SERVICIO - BORRAR PRODUCTO ::::::::");
        ProductoEntity productoEntity = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ProductoNoEncontradoException(HttpStatus.BAD_REQUEST, idProducto.toString()));
        productoEntity.setUltimaModificacion(new Date());
        productoRepository.delete(productoEntity);
        return true;
    }

    @Override
    public Map<String, Object> listarProductos(Integer paginaActual, Integer totalRegistros, String descripcion, String codigo) {
        log.info(":::::::: SERVICIO - LISTAR PRODUCTOS ::::::::");
        PageRequest pageRequest = PageRequest.of(paginaActual,totalRegistros);
        Specification<ProductoEntity> specification = Specification
                .where(productoSpecification.filtroDescripcion(descripcion))
                .and(productoSpecification.filtroCodigo(codigo));
        Page<ProductoEntity> page = productoRepository.findAll(specification, pageRequest);
        List<ProductoDTO> listaProductos = page.getContent().stream().map(producto -> modelMapper.map(producto, ProductoDTO.class)).collect(Collectors.toList());
        return Util.itemMapeoPaginado(listaProductos, page.getNumber(),page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public ProductoDTO buscarProductoPorID(Long id) {
        log.info(":::::::: SERVICIO - BUSCAR PRODUCTO POR ID::::::::");
        ProductoEntity productoEntity = productoRepository.findById(id).orElseThrow(() -> new ProductoNoEncontradoException(HttpStatus.BAD_REQUEST, id.toString()));
        return modelMapper.map(productoEntity, ProductoDTO.class);
    }

    @Override
    public Boolean ventaProducto(List<ProductoDTO> listaProductos) {
        listaProductos.forEach(producto -> {
            ProductoEntity productoEntity = productoRepository.findById(producto.getId())
                    .orElseThrow(() -> new ProductoNoEncontradoException(HttpStatus.BAD_REQUEST, producto.getId().toString()));
            Integer cantidadActual = productoEntity.getCantidad();
            if (cantidadActual == 0)
                throw new StockNegativoException(HttpStatus.BAD_REQUEST, producto.getId().toString());
            productoEntity.setCantidad(cantidadActual - 1);
            productoEntity.setUltimaModificacion(new Date());
            productoRepository.save(productoEntity);
        });
        return Boolean.TRUE;
    }
}
