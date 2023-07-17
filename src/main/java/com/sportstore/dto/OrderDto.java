package com.sportstore.dto;

import lombok.Data;

@Data
public class OrderDto {
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String zipCode;
    private String street;
    private String streetNo;
    private String homeNo;
}
