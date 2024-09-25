package com.vc.pdv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.vc.pdv.dto.ResponseDTO;
import com.vc.pdv.dto.SaleDTO;
import com.vc.pdv.exceptions.InvalidOperationException;
import com.vc.pdv.exceptions.NoItemException;
import com.vc.pdv.services.SaleService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity<>(saleService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        try {
            return new ResponseEntity<>(saleService.findById(id), HttpStatus.OK);
        } catch (NoItemException e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> post(@Valid @RequestBody SaleDTO saleDTO) {
        try {
            long id = saleService.save(saleDTO);
            return new ResponseEntity<>("Venda realizada com sucesso. #" + id,
                    HttpStatus.CREATED);
        } catch (NoItemException | InvalidOperationException e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
