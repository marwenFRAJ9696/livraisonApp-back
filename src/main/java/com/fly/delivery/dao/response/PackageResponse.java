package com.fly.delivery.dao.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageResponse {
    private Integer id;
    private String fullName;
    private String factureNumber;
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
    private Double priceDelivery;
    private boolean isPackageCanBeOpen;
    private String comment;
    private String status;
    private Date createdDate;
    private String emailUser;
    private boolean isEchange;
    private boolean isFragile;
    private boolean isPetit;
    private boolean isGrand;
    private boolean isExtraLarge;
    private boolean isMoyen;
    public void isPackageCanBeOpen(boolean packageCanBeOpen) {
        this.isPackageCanBeOpen = packageCanBeOpen;
    }


    public void isEchange(boolean echange) {
        this.isEchange = echange;
    }

    public void isFragile(boolean fragile) {
        this.isFragile = fragile;
    }

    public void isMoyen(boolean moyen) {
        this.isMoyen = moyen;
    }

    public void isPetit(boolean petit) {
        this.isPetit = petit;
    }

    public void isGrand(boolean grand) {
        this.isGrand = grand;
    }

    public void isExtraLarge(boolean extraLarge) {
        this.isExtraLarge = extraLarge;
    }
}
