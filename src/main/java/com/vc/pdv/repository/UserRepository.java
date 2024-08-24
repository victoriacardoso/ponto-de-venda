package com.vc.pdv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vc.pdv.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{
    
}

