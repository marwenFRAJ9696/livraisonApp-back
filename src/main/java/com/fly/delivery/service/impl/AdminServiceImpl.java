package com.fly.delivery.service.impl;


import com.fly.delivery.dao.response.SignUpResponse;
import com.fly.delivery.entities.User;
import com.fly.delivery.repository.UserRepository;
import com.fly.delivery.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<SignUpResponse> getAllClientAccount() {
        Collection<SignUpResponse> comptes = new ArrayList<>();
        List<User> users = userRepository.findAll();
        users.stream().forEach(u -> {
            SignUpResponse newUser = new SignUpResponse();
            newUser.setCin(u.getCin());
            newUser.setEmail(u.getEmail());
            newUser.setCampanyName(u.getCampanyName());
            newUser.setFirstName(u.getFirstName());
            newUser.setLastName(u.getLastName());
            newUser.setPassword(u.getPassword());
            newUser.setTaxIdentificationNumber(u.getTaxIdentificationNumber());
            newUser.setTelNumber(u.getTelNumber());
            newUser.setAddress(u.getAddress());
            newUser.setCity(u.getCity());
            newUser.setCodePostal(u.getCodePostal());
            newUser.setPriceOfDelivery(u.getPriceOfDelivery());
            newUser.setRole(u.getRole().name());
            newUser.setSexe(u.getSexe()!= null ? u.getSexe().name() : null);
            comptes.add(newUser);
        });
        return comptes;
    }

    @Override
    public SignUpResponse getClientAccountByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        SignUpResponse newUser = new SignUpResponse();
        if (user.isPresent()) {
            User u = user.get();
            newUser.setCin(u.getCin());
            newUser.setEmail(u.getEmail());
            newUser.setCampanyName(u.getCampanyName());
            newUser.setFirstName(u.getFirstName());
            newUser.setLastName(u.getLastName());
            newUser.setPassword(u.getPassword());
            newUser.setTaxIdentificationNumber(u.getTaxIdentificationNumber());
            newUser.setTelNumber(u.getTelNumber());
            newUser.setAddress(u.getAddress());
            newUser.setCity(u.getCity());
            newUser.setCodePostal(u.getCodePostal());
            newUser.setPriceOfDelivery(u.getPriceOfDelivery());
            newUser.setRole(u.getRole().name());
            newUser.setSexe(u.getSexe()!= null ? u.getSexe().name() : null);
        }
        return newUser;
    }
}
