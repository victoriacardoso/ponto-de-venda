package com.vc.pdv.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vc.pdv.dto.UserDTO;
import com.vc.pdv.exceptions.NoItemException;
import com.vc.pdv.model.UserModel;
import com.vc.pdv.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.isEnabled())).collect(Collectors.toList());
    }

    public UserDTO save(UserDTO user) {
        UserModel userModel = new UserModel();
        userModel.setEnabled(user.isEnabled());
        userModel.setName(user.getName());
        userRepository.save(userModel);
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }

    public UserDTO findById(UserModel userModel) {
        Optional<UserModel> userToFind = userRepository.findById(userModel.getId());
        
        if (!userToFind.isPresent()) {
            throw new NoItemException("Usuário não encontrado!");
        }
        UserModel user = userToFind.get();
        return new UserDTO(user.getId(), user.getName(), user.isEnabled());
    }

    public UserDTO update(UserDTO user) {
        Optional<UserModel> userToEdit = userRepository.findById(user.getId());
        UserModel userModel = new UserModel();
        userModel.setEnabled(user.isEnabled());
        userModel.setName(user.getName());

        if (!userToEdit.isPresent()) {
            throw new NoItemException("Usuário não encontrado!");
        }
        userRepository.save(userModel);
        return new UserDTO(userModel.getId(), userModel.getName(), userModel.isEnabled());

    }

    public void deleteById(long id) {
        if (!userRepository.existsById(id)) {
            throw new NoItemException("Usuário não encontrado.");
        }
        userRepository.deleteById(id);

    }
}
