package com.example.sportstore.integration.service;

import com.example.sportstore.dto.ProductDto;
import com.example.sportstore.entity.Product;
import com.example.sportstore.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void afterEachTest() {
        jdbcTemplate.execute("set foreign_key_checks = 0;");
        jdbcTemplate.execute("truncate table product;");
    }

    @Test
    public void testAddProduct() {
        //given
        ProductDto productDto = new ProductDto();
        productDto.setName("Product");
        productDto.setDescription("Description");
        productDto.setImage("sample.jpg");
        productDto.setPrice("12.34");

        MultipartFile multipartFile = mock(MultipartFile.class);

        //when
        productService.addProduct(productDto, multipartFile);

        //then
        String sql = "select id, name, description, image, price from product where name='Product';";
        RowMapper<Product> rowMapper = (rs, rowNum) -> new Product(rs.getLong("id"),
                rs.getString("name"), rs.getString("description"), rs.getString("image"),
                rs.getBigDecimal("price"));

        Product product = jdbcTemplate.queryForObject(sql, rowMapper);

        assertEquals("Product", product.getName());
        assertEquals("Description", product.getDescription());
        assertEquals("sample.jpg", product.getImage());
        assertEquals("12.34", product.getPrice().toString());
    }

    @Test
    public void testGetProducts(){
        //given
        ProductDto productDto1 = new ProductDto();
        productDto1.setName("Product1");
        productDto1.setDescription("Description1");
        productDto1.setImage("sample1.jpg");
        productDto1.setPrice("12.34");

        ProductDto productDto2 = new ProductDto();
        productDto2.setName("Product2");
        productDto2.setDescription("Description2");
        productDto2.setImage("sample2.jpg");
        productDto2.setPrice("12.36");

        ProductDto productDto3 = new ProductDto();
        productDto3.setName("Product3");
        productDto3.setDescription("Description3");
        productDto3.setImage("sample3.jpg");
        productDto3.setPrice("12.38");

        MultipartFile multipartFile = mock(MultipartFile.class);
        productService.addProduct(productDto1, multipartFile);
        productService.addProduct(productDto2, multipartFile);
        productService.addProduct(productDto3, multipartFile);

        //when

        List<ProductDto> result =  productService.getProducts();

        //then
        assertEquals(3, result.size());
    }

    @Test
    public void getProductById(){
        //given
        ProductDto productDto1 = new ProductDto();
        productDto1.setId(1L);
        productDto1.setName("Product1");
        productDto1.setDescription("Description1");
        productDto1.setImage("sample1.jpg");
        productDto1.setPrice("12.34");

        ProductDto productDto2 = new ProductDto();
        productDto2.setId(2L);
        productDto2.setName("Product2");
        productDto2.setDescription("Description2");
        productDto2.setImage("sample2.jpg");
        productDto2.setPrice("12.36");

        ProductDto productDto3 = new ProductDto();
        productDto3.setId(3L);
        productDto3.setName("Product3");
        productDto3.setDescription("Description3");
        productDto3.setImage("sample3.jpg");
        productDto3.setPrice("12.38");

        MultipartFile multipartFile = mock(MultipartFile.class);
        productService.addProduct(productDto1, multipartFile);
        productService.addProduct(productDto2, multipartFile);
        productService.addProduct(productDto3, multipartFile);

        //when

        ProductDto result =  productService.getProductById(2L);

        //then
        assertEquals(2L, result.getId());
        assertEquals("Product2", result.getName());
        assertEquals("Description2", result.getDescription());
        assertEquals("sample2.jpg", result.getImage());
        assertEquals("12.36", result.getPrice());
    }

    @Test
    public void removeProductById() {
        ProductDto productDto1 = new ProductDto();
        productDto1.setId(1L);
        productDto1.setName("Product1");
        productDto1.setDescription("Description1");
        productDto1.setImage("sample1.jpg");
        productDto1.setPrice("12.34");

        ProductDto productDto2 = new ProductDto();
        productDto2.setId(2L);
        productDto2.setName("Product2");
        productDto2.setDescription("Description2");
        productDto2.setImage("sample2.jpg");
        productDto2.setPrice("12.36");

        ProductDto productDto3 = new ProductDto();
        productDto3.setId(3L);
        productDto3.setName("Product3");
        productDto3.setDescription("Description3");
        productDto3.setImage("sample3.jpg");
        productDto3.setPrice("12.38");

        MultipartFile multipartFile = mock(MultipartFile.class);
        productService.addProduct(productDto1, multipartFile);
        productService.addProduct(productDto2, multipartFile);
        productService.addProduct(productDto3, multipartFile);

        //when
        productService.removeProductById(1L);

        //then
        List<ProductDto> products =  productService.getProducts();
        assertEquals(2, products.size());

    }
}
