package com.example.sportstore.controller;

import com.example.sportstore.dto.OrderDto;
import com.example.sportstore.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String allOrders(Model model) {
        List<OrderDto> ordersFromDb = orderService.getOrders();
        model.addAttribute("orders", ordersFromDb);
        return "orders";
    }
}
