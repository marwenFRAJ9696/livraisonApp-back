package com.fly.delivery.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "colis")
public class Package {
    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String factureNumber;
    private String fullName;
    private String governorate;
    private String city;
    private String fullAddress;
    private String telNumber;
    private String telNumber2;
    private String designation;
    private long articleNumber;
    private long packageNumber;
    private PaymentMode paymentMode;
    private DeliveryType deliveryType;
    private Double priceNet;
    private boolean isPackageCanBeOpen;
    private String comment;
    private Status status;
    private Date createdDate;
    private boolean isEchange;
    private boolean isFragile;
    private boolean isPetit;
    private boolean isGrand;
    private boolean isExtraLarge;
    private boolean isMoyen;
    @ManyToOne
    private User user;



}
