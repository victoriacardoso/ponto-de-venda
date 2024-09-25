package com.vc.pdv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vc.pdv.dto.ResponseDTO;
import com.vc.pdv.exceptions.NoItemException;
import com.vc.pdv.model.ProductModel;
import com.vc.pdv.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> post(@Valid @RequestBody ProductModel productModel) {
        try {
            return new ResponseEntity<>(productService.save(productModel), HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("")
    public ResponseEntity<?> put(@RequestBody ProductModel productModel) {
        try {
            return new ResponseEntity<>(productService.update(productModel), HttpStatus.OK);
        }
        catch(NoItemException e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }

    @DeleteMapping("{id}") 
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            productService.delete(id);
            return new ResponseEntity<>(new ResponseDTO<>("Produto removido com sucesso"), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
    }
}
