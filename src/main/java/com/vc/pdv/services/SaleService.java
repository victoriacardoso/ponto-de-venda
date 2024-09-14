package com.vc.pdv.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vc.pdv.dto.ProductDTO;
import com.vc.pdv.dto.SaleDTO;
import com.vc.pdv.model.ItemSaleModel;
import com.vc.pdv.model.ProductModel;
import com.vc.pdv.model.SaleModel;
import com.vc.pdv.model.UserModel;
import com.vc.pdv.repository.ItemSaleRepository;
import com.vc.pdv.repository.ProductRepository;
import com.vc.pdv.repository.SaleRepository;
import com.vc.pdv.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ItemSaleRepository itemSaleRepository;

    @Transactional
    public long save(SaleDTO sale) {
        UserModel user = userRepository.findById(sale.getUserId()).get();
        SaleModel newSale = new SaleModel();
        newSale.setUser(user);
        newSale.setDate(LocalDate.now());
        newSale = saleRepository.save(newSale);
        saveItemSale(getItemSale(sale.getItems()), newSale);

        return newSale.getId();
    }

    private void saveItemSale(List<ItemSaleModel> items, SaleModel newSale) {
        for (ItemSaleModel item : items) {
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }

    }

    private List<ItemSaleModel> getItemSale(List<ProductDTO> products) {
        return products.stream().map(item -> {
            ProductModel productModel = productRepository.getReferenceById(item.getProductId());
            ItemSaleModel itemSale = new ItemSaleModel();
            itemSale.setProduct(productModel);
            itemSale.setQuantity(item.getQuantity());
            return itemSale;
        }).collect(Collectors.toList());
    }
}
