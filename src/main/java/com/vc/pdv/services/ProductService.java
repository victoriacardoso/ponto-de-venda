package com.vc.pdv.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vc.pdv.dto.ProductDTO;
import com.vc.pdv.model.ProductModel;
import com.vc.pdv.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(product -> new ProductDTO(product.getId(), product.getQuantity())).collect(Collectors.toList());
    }

    public ProductDTO save(ProductModel productModel) {
        productRepository.save(productModel);
        return new ProductDTO(productModel.getId(), productModel.getQuantity());
    }

    public ProductDTO findById(long id) {
        Optional<ProductModel> productToFind = productRepository.findById(id);

        if (!productToFind.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return new ProductDTO(productToFind.get().getId(), productToFind.get().getQuantity());
    }

    public ProductDTO update(ProductModel productModel) {
        Optional<ProductModel> productToEdit = productRepository.findById(productModel.getId());
        if (!productToEdit.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        return new ProductDTO(productToEdit.get().getId(), productToEdit.get().getQuantity());

    }

    public void delete(long id) {
        Optional<ProductModel> productToFind = productRepository.findById(id);

        if (!productToFind.isPresent()) {
            throw new EmptyResultDataAccessException(1);
        }
        productRepository.delete(productToFind.get());
    }
}
