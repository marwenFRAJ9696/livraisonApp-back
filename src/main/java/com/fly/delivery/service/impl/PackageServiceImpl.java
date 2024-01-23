package com.fly.delivery.service.impl;



import com.fly.delivery.dao.request.PackageRequest;
import com.fly.delivery.dao.response.ColisResponse;
import com.fly.delivery.dao.response.PackageResponse;
import com.fly.delivery.dao.response.StatusResponse;
import com.fly.delivery.entities.*;
import com.fly.delivery.entities.Package;
import com.fly.delivery.repository.PackageRepository;
import com.fly.delivery.repository.UserRepository;
import com.fly.delivery.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PackageRepository packageRepository;

    @Override
    public ColisResponse addPackage(PackageRequest request) {
        String message =null;
        if(request.getEmailUser()== null){
            message="Email is Mandatory!";
            return ColisResponse.builder().message(message).build();
        }
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmailUser());
        User user = null;
        if(optionalUser.isPresent()){
            user= optionalUser.get();
        }else {
            message = "Email not found!";
            return ColisResponse.builder().message(message).build();
        }
        var pack = Package.builder().fullName(request.getFullName()).governorate(request.getGovernorate())
                .city(request.getCity()).telNumber(request.getTelNumber())
                .designation(request.getDesignation()).fullAddress(request.getFullAddress())
                .articleNumber(request.getArticleNumber())
                .packageNumber(request.getPackageNumber())
                .isPackageCanBeOpen(request.getIsPackageCanBeOpen().equals("OUI") ? true : false)
                .deliveryType(Enum.valueOf(DeliveryType.class,request.getDeliveryType()))
                .paymentMode(Enum.valueOf(PaymentMode.class,request.getPaymentMode()))
                .priceNet(request.getPriceNet())
                .status(Status.NOT_STARTED)
                .comment(request.getComment())
                .user(user).build();
        packageRepository.save(pack);
        message = "Package added successfully!";

        return ColisResponse.builder().message(message).build();
    }

    @Override
    public Collection<PackageResponse> getPackageByClient(String email) {
        Collection<PackageResponse> packageResponses = new ArrayList<>();
        Optional<User> optionaluser = userRepository.findByEmail(email);
        if(optionaluser.isPresent()) {
            User user = optionaluser.get();
            Collection<Package> packages = user.getPackages();
            packages.stream().forEach(p -> {
                PackageResponse packageResponse = new PackageResponse();
                packageResponse.setCity(p.getCity());
                packageResponse.setPackageNumber(p.getPackageNumber());
                packageResponse.setArticleNumber(p.getArticleNumber());
                packageResponse.setComment(p.getComment());
                packageResponse.isPackageCanBeOpen(p.isPackageCanBeOpen());
                packageResponse.setDesignation(p.getDesignation());
                packageResponse.setDeliveryType(p.getDeliveryType().name());
                packageResponse.setFullAddress(p.getFullAddress());
                packageResponse.setFullName(p.getFullName());
                packageResponse.setPaymentMode(p.getPaymentMode().name());
                packageResponse.setGovernorate(p.getGovernorate());
                packageResponse.setPriceNet(p.getPriceNet());
                packageResponse.setPriceDelivery(user.getPriceOfDelivery());
                packageResponse.setTelNumber(user.getTelNumber());
                packageResponse.setStatus(p.getStatus()!= null ? p.getStatus().name() : null);
                packageResponse.setId(p.getId());
                packageResponses.add(packageResponse);
            });
        }
        return packageResponses;
    }

    @Override
    public StatusResponse changeStatus(Integer id, String status) {
        Optional<Package> packageOptional = packageRepository.findById(id);
        StatusResponse statusResponse=new StatusResponse();
        if(packageOptional.isPresent()){
            Package aPackage = packageOptional.get();
            Status oldStatus = Enum.valueOf(Status.class, status);
            if (oldStatus == Status.NOT_STARTED) {
                aPackage.setStatus(Status.IN_PROGRESS);
            } else if (oldStatus == Status.IN_PROGRESS) {
                aPackage.setStatus(Status.DELIVERED);
            } else if (oldStatus == Status.DELIVERED) {
                aPackage.setStatus(Status.BOOKED);
            }
            packageRepository.save(aPackage);
            statusResponse.setStatus(aPackage.getStatus().name());
        }
        return statusResponse;
    }
}
