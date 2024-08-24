package com.vc.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vc.pdv.model.ItemSaleModel;

@Repository
public interface ItemSaleRepository extends JpaRepository<ItemSaleModel, Long>{
    
}
