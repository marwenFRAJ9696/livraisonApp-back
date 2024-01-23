package com.fly.delivery.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponse {
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
    private String sexe;
    private double  priceOfDelivery;

}
