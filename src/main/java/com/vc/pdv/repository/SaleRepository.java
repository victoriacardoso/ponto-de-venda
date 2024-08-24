package com.vc.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vc.pdv.model.SaleModel;

@Repository
public interface SaleRepository extends JpaRepository<SaleModel, Long>{
    
}
