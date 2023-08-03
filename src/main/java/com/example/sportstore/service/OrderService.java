package com.example.sportstore.service;

import com.example.sportstore.ShoppingCart;
import com.example.sportstore.dto.OrderDto;
import com.example.sportstore.dto.ProductDto;
import com.example.sportstore.entity.Order;
import com.example.sportstore.entity.Product;
import com.example.sportstore.exception.ObjectNotFoundException;
import com.example.sportstore.repository.OrderRepository;
import com.example.sportstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public void makeOrder(OrderDto orderDto, ShoppingCart shoppingCart) {
        Order order = new Order(orderDto.getFirstName() + " " + orderDto.getLastName(), orderDto.getEmail(), orderDto.getCity(), orderDto.getZipCode(),
                orderDto.getStreet(), orderDto.getStreetNo(), orderDto.getHomeNo());
        order.setPrice(shoppingCart.getTotalCost());
        List<Long> productIds = shoppingCart.getProducts().stream().map(ProductDto::getId).toList();
        List<Product> orderedProducts = productRepository.findProductByIdIn(productIds);
        order.setProducts(orderedProducts);
        orderRepository.save(order);
        shoppingCart.clearShoppingCart();
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream().map(o -> new OrderDto(o.getId(), o.getCustomerFullName(), o.getCustomerEmail(),
                o.getCity(), o.getZipCode(), o.getStreet(), o.getStreetNo(), o.getHomeNo(), o.getPrice().toString())).toList();
    }

    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException("Order with id: " + orderId + " not found"));
        return new OrderDto(order.getId(), order.getCustomerFullName(), order.getCustomerEmail(), order.getCity(),
                order.getZipCode(), order.getStreet(), order.getStreetNo(), order.getHomeNo(), order.getPrice().toString());
    }
}
