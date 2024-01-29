package com.fly.delivery.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fly.delivery.dao.request.SignUpRequest;
import com.fly.delivery.dao.request.SigninRequest;
import com.fly.delivery.dao.response.JwtAuthenticationResponse;
import com.fly.delivery.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://102.211.210.49:4200","http://www.flydelivery.com.tn:4200","http://www.flydelivery.com.tn"
        ,"https://www.flydelivery.com.tn:4200","https://www.flydelivery.com.tn"}, allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
