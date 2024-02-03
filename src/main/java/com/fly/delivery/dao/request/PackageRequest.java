package com.fly.delivery.dao.request;

import com.fly.delivery.entities.DeliveryType;
import com.fly.delivery.entities.PaymentMode;
import com.fly.delivery.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageRequest {
    private Integer id;
    private String fullName;
    private String governorate;
    private String city;
    private String fullAddress;
    private String telNumber;
    private String telNumber2;
    private String designation;
    private long articleNumber;
    private long packageNumber;
    private String paymentMode;
    private String deliveryType;
    private Double priceNet;
    private String isPackageCanBeOpen;
    private String comment;
    private String emailUser;
    private String status;
    private String isEchange;
    private String isFragile;
    private String isPetit;
    private String isGrand;
    private String isExtraLarge;
    private String isMoyen;
}
