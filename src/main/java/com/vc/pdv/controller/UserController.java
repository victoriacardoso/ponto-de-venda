package com.vc.pdv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vc.pdv.dto.ResponseDTO;
import com.vc.pdv.dto.UserDTO;
import com.vc.pdv.exceptions.NoItemException;
import com.vc.pdv.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> post(@Valid @RequestBody UserDTO user) {
        try {
            user.setEnabled(true);
            return new ResponseEntity<>(userService.save(user),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> put(@RequestBody UserDTO userDTO) {
        try {
            return new ResponseEntity<>(userService.update(userDTO), HttpStatus.OK);
        } catch (NoItemException e) {
            return new ResponseEntity<>(
                    new ResponseDTO<>(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>(new ResponseDTO<>("Usuário removido com sucesso"), HttpStatus.OK);
        } catch (NoItemException e) {
            return new ResponseEntity<>(new ResponseDTO<>("Não foi possível localizar o usuário."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
