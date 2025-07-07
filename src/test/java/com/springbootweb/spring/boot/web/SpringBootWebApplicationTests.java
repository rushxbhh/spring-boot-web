package com.springbootweb.spring.boot.web;

import com.springbootweb.spring.boot.web.clients.ProductClient;
import com.springbootweb.spring.boot.web.dto.ProductDTO;
import com.springbootweb.spring.boot.web.entities.User;
import com.springbootweb.spring.boot.web.services.JWTService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class SpringBootWebApplicationTests {

	@Autowired
    private ProductClient productClient;
	@Autowired
	private JWTService jwtService;

    @Test
	void getallproducts(){
		List<ProductDTO> productDTOList = productClient.getAllProduct();
		System.out.println(productDTOList);
	}
	@Test
	void getProduct( ){
		ProductDTO productDTO = productClient.getProduct(1L);
		System.out.println(productDTO);
	}

	@Test
	void addProduct() {
		ProductDTO productDTO = new ProductDTO( null , "jpa" ,"tech", 23.5 , true , null);
		ProductDTO savedDTO = productClient.addProduct(productDTO);
		System.out.println(savedDTO);
	}

	@Test
	void generateToken() {
		User user = new User( 2L , "rushabh" ,"12345");
		String token = jwtService.generateToken(user);
		System.out.println(token);

		Long id = jwtService.getUserIdfromToken(token);
		System.out.println(id);
	}
}
