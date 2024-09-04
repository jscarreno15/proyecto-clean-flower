package com.clean.flower.repository;

import com.clean.flower.entity.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    Page<ProductoEntity> findAll(Specification<ProductoEntity> specification, Pageable pageable);
    Optional<ProductoEntity> findByCodigo(String codigo);
}
