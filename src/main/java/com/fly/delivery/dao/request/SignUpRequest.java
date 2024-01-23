package com.fly.delivery.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cin;
    private String campanyName;
    private String taxIdentificationNumber;
    private Long telNumber;
    private String address;
    private String city;
    private String codePostal;
    private double  priceOfDelivery;
    private String  sexe;
}
