package com.fly.delivery.service.impl;

import com.fly.delivery.entities.Gender;
import com.fly.delivery.entities.Role;
import com.fly.delivery.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fly.delivery.dao.request.SignUpRequest;
import com.fly.delivery.dao.request.SigninRequest;
import com.fly.delivery.dao.response.JwtAuthenticationResponse;
import com.fly.delivery.repository.UserRepository;
import com.fly.delivery.service.AuthenticationService;
import com.fly.delivery.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN).cin(request.getCin()).taxIdentificationNumber(request.getTaxIdentificationNumber())
                .campanyName(request.getCampanyName()).telNumber(request.getTelNumber())
                .codePostal(request.getCodePostal()).address(request.getAddress()).city(request.getCity()).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signupForUser(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).cin(request.getCin()).taxIdentificationNumber(request.getTaxIdentificationNumber())
                .campanyName(request.getCampanyName()).telNumber(request.getTelNumber())
                .codePostal(request.getCodePostal()).address(request.getAddress()).city(request.getCity())
                .priceOfDelivery(request.getPriceOfDelivery())
                .sexe(Enum.valueOf(Gender.class,request.getSexe())).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
