package com.vc.pdv.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.vc.pdv.dto.ProductDTO;
import com.vc.pdv.dto.ProductInfoDTO;
import com.vc.pdv.dto.SaleDTO;
import com.vc.pdv.dto.SaleInfoDTO;
import com.vc.pdv.exceptions.InvalidOperationException;
import com.vc.pdv.exceptions.NoItemException;
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
        UserModel user = userRepository.findById(sale.getUserId())
                .orElseThrow(() -> new NoItemException("Usuário não encontrado!"));
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
        if (products.isEmpty()) {
            throw new InvalidOperationException("Não é possível adicionar a venda sem itens.");
        }
        return products.stream().map(item -> {
            ProductModel productModel = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new NoItemException("Item de venda não encontrado."));
            ItemSaleModel itemSale = new ItemSaleModel();
            itemSale.setProduct(productModel);
            itemSale.setQuantity(item.getQuantity());
            if (productModel.getQuantity() == 0) {
                throw new NoItemException("Produto sem estoque.");
            } else if (productModel.getQuantity() < item.getQuantity()) {
                throw new NoItemException(String.format(
                        "A quantidade de itens da venda (%s) é maior que a quantidade disponível no estoque (%s)",
                        item.getQuantity(), productModel.getQuantity()));

            }
            int total = productModel.getQuantity() - item.getQuantity();
            productModel.setQuantity(total);
            productRepository.save(productModel);

            return itemSale;
        }).collect(Collectors.toList());
    }

    public List<SaleInfoDTO> findAll() {
        return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
    }

    public SaleInfoDTO findById(long id) {
        SaleModel saleToFind = saleRepository.findById(id)
                .orElseThrow(() -> new NoItemException("Venda não encontrada."));

        return getSaleInfo(saleToFind);

    }

    public SaleInfoDTO getSaleInfo(SaleModel saleModel) {
        return SaleInfoDTO.builder().user(saleModel.getUser().getName())
                .date(saleModel.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .products(getProductInfo(saleModel.getItems())).build();
    }

    public List<ProductInfoDTO> getProductInfo(List<ItemSaleModel> items) {
        if(CollectionUtils.isEmpty(items)) {
            return Collections.emptyList();
        }
        return items.stream().map(
            item -> ProductInfoDTO
            .builder()
            .id(item.getId())
            .description(item.getProduct().getDescription())
            .quantity(item.getQuantity()).build()
        ).collect(Collectors.toList());
    }

}
