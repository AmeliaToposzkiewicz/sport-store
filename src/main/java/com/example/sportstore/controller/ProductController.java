package com.example.sportstore.controller;

import com.example.sportstore.ShoppingCart;
import com.example.sportstore.dto.ProductDto;
import com.example.sportstore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@Slf4j
public class ProductController {
    private final ShoppingCart shoppingCart;
    private final ProductService productService;


    public ProductController(ShoppingCart shoppingCart, ProductService productService) {
        this.shoppingCart = shoppingCart;
        this.productService = productService;
    }

    @GetMapping
    public String allProducts(Model model){
        List<ProductDto> productsFromDb = productService.getProducts();
        model.addAttribute("products", productsFromDb);
        int cartSize = shoppingCart.getCartSize();
        model.addAttribute("cartSize", cartSize);
        return "show-all-products";
    }
}
