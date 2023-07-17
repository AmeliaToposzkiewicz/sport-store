package com.sportstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerFullName;

    private String customerEmail;

    private String city;

    private String zipCode;

    private String street;

    private String streetNo;

    private String homeNo;

    private BigDecimal price;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    public Order(String customerFullName, String customerEmail, String city, String zipCode, String street, String streetNo, String homeNo) {
        this.customerFullName = customerFullName;
        this.customerEmail = customerEmail;
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.streetNo = streetNo;
        this.homeNo = homeNo;
    }
}
