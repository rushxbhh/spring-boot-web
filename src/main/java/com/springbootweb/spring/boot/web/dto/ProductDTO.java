package com.springbootweb.spring.boot.web.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {

        private Long id;
        private String name;
        private String category;
        private Double price;
        private Boolean inStock;
        private LocalDate assignedDate;


    }


