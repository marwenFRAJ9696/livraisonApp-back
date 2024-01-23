package com.fly.delivery.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageResponse {
    private Integer id;
    private String fullName;
    private String governorate;
    private String city;
    private String fullAddress;
    private Long telNumber;
    private String designation;
    private long articleNumber;
    private long packageNumber;
    private String paymentMode;
    private String deliveryType;
    private Double priceNet;
    private Double priceDelivery;
    private boolean isPackageCanBeOpen;
    private String comment;
    private String status;
    public void isPackageCanBeOpen(boolean packageCanBeOpen) {
        this.isPackageCanBeOpen = packageCanBeOpen;
    }


}
