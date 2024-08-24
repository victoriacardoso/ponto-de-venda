package com.vc.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vc.pdv.model.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long>{
    
}
