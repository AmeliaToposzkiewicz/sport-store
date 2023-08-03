package com.example.sportstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String zipCode;
    private String street;
    private String streetNo;
    private String homeNo;
    private String price;

    public OrderDto(Long id, String fullName, String email, String city, String zipCode, String street, String streetNo,
                    String homeNo, String price) {
        this.id = id;
        String[] firstAndLastName = fullName.split(" ");
        this.firstName  = firstAndLastName[0];
        this.lastName = firstAndLastName[1];
        this.email = email;
        this.city = city;
        this.zipCode = zipCode;
        this.street = street;
        this.streetNo = streetNo;
        this.homeNo = homeNo;
        this.price = price;
    }
}
