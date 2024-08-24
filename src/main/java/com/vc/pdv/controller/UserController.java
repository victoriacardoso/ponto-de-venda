package com.vc.pdv.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vc.pdv.model.UserModel;
import com.vc.pdv.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> post(@RequestBody UserModel userModel) {
        try {
            userModel.setEnabled(true);
            return new ResponseEntity<>(userRepository.save(userModel), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<?> put(@RequestBody UserModel userModel) {
        Optional<UserModel> userToEdit = userRepository.findById(userModel.getId());

        if(userToEdit.isPresent()) {
            userRepository.save(userModel);
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();

    }
    
}
