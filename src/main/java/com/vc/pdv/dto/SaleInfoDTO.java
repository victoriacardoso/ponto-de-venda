package com.vc.pdv.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleInfoDTO {
    private String user;
    private String date;
    private List<ProductInfoDTO> products;
}
