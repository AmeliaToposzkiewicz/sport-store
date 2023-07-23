package com.example.sportstore.controller;

import com.example.sportstore.ShoppingCart;
import com.example.sportstore.dto.OrderDto;
import com.example.sportstore.dto.ProductDto;
import com.example.sportstore.service.OrderService;
import com.example.sportstore.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("cart")
public class CartController {
    private final ShoppingCart shoppingCart;
    private final ProductService productService;
    private final OrderService orderService;

    public CartController(ShoppingCart shoppingCart, ProductService productService, OrderService orderService) {
        this.shoppingCart = shoppingCart;
        this.productService = productService;
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public String add(@RequestParam(name = "productId") Long productId) {
        ProductDto productDto = productService.getProductById(productId);
        shoppingCart.addToCart(productDto);
        return "redirect:/products";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam(name = "productId") Long productId){
        ProductDto productDto = productService.getProductById(productId);
        shoppingCart.removeFromCart(productDto);
        return "redirect:/cart";
    }

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("products", shoppingCart.getProducts());
        model.addAttribute("totalCost", shoppingCart.getTotalCost() != null ? shoppingCart.getTotalCost().toString() : "0");
        model.addAttribute("orderDto", new OrderDto());
        int cartSize = shoppingCart.getCartSize();
        model.addAttribute("cartSize", cartSize);

        return "cart";
    }

    @PostMapping
    @RequestMapping("/makeOrder")
    public String makeOrder(OrderDto orderDto) {
        orderService.makeOrder(orderDto, shoppingCart);
        return "redirect:/products/summary";
    }
}
