package com.springbootweb.spring.boot.web.clients;

import com.springbootweb.spring.boot.web.dto.ProductDTO;

import java.util.List;

public interface ProductClient {

    List<ProductDTO> getAllProduct();



    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO getProduct(long l);
}
