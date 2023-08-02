package com.example.sportstore.controller;

import com.example.sportstore.dto.ProductDto;
import com.example.sportstore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @RequestMapping("/view/add")
    public String getAddProductView(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        return "add-product";
    }

    @PostMapping
    @RequestMapping("/products/add")
    public String addProduct(ProductDto productDto, @RequestParam("img") MultipartFile file) {
        productDto.setImage(file.getOriginalFilename());
        productService.addProduct(productDto, file);
        return "redirect:/admin/view/add";
    }

    @GetMapping
    @RequestMapping("/view/remove")
    public String getRemoveProductView(Model model) {
        List<ProductDto> productsFromDb = productService.getProducts();
        model.addAttribute("products", productsFromDb);
        return "remove-product";
    }

    @PostMapping
    @RequestMapping("/products/remove")
    public String removeProduct(@RequestParam(name = "productId") Long productId) {
        productService.removeProductById(productId);
        return "redirect:/admin/view/remove";
    }
}
