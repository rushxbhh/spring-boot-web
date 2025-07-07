package com.springbootweb.spring.boot.web.clients.impl;

import com.springbootweb.spring.boot.web.advices.ApiResponse;
import com.springbootweb.spring.boot.web.clients.ProductClient;
import com.springbootweb.spring.boot.web.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
@RequiredArgsConstructor
@Service

public class ProductClientImpl implements ProductClient {

    Logger log = LoggerFactory.getLogger(ProductClient.class);
    private  final RestClient restClient;

    @Override
    public List<ProductDTO> getAllProduct() {
        log.trace(" trying to retrieve all the employees");
        try {
          ApiResponse<List<ProductDTO>> productDTOList = restClient.get()
                    .uri("products")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
          log.debug("successfully retrieved all the getallemployee");
          log.trace("retrieved all emp list : {}" , productDTOList);
            return productDTOList.getData();
        }
        catch (Exception e)
        {
            log.error("error accured in getallemp");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductDTO getProduct(long id) {
        log.trace(" trying to the the product by id : {}" , id);
        try {
            ApiResponse<ProductDTO> productResponse = restClient.get()
                    .uri("products/{id}" , id)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return productResponse.getData();
        } catch (Exception e) {
            log.error(" error ocuured in getting the product");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        log.trace(" trying to create a product : {}" , productDTO);
        try {
            ApiResponse<ProductDTO> createProd = restClient.post()
                    .uri("products")
                    .body(productDTO)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return createProd.getData();
        } catch (Exception e) {
            log.error(" error ocuured in creating the product");
            throw new RuntimeException(e);
        }
    }
}
