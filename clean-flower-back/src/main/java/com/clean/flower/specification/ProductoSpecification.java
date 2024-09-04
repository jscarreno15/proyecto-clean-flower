package com.clean.flower.specification;

import com.clean.flower.entity.ProductoEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductoSpecification {
    public Specification<ProductoEntity> filtroDescripcion(String descripcion){
        return (productoEntity, query , criteriaBuilder) ->
                descripcion.isBlank() ? criteriaBuilder.conjunction() : criteriaBuilder.like(productoEntity.get("descripcion"),'%'+ descripcion+'%');
    }

    public Specification<ProductoEntity> filtroCodigo(String codigo){
        return (productoEntity, query , criteriaBuilder) ->
                codigo.isBlank() ? criteriaBuilder.conjunction() : criteriaBuilder.like(productoEntity.get("codigo"),'%'+ codigo+'%');
    }
}
