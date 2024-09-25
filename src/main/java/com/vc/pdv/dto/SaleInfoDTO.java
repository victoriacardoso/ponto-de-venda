package com.vc.pdv.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleInfoDTO {
    private String user;
    private String date;
    private List<ProductInfoDTO> products;
}
