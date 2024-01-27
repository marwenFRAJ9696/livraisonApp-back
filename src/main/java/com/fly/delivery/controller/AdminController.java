package com.fly.delivery.controller;

import com.fly.delivery.dao.request.SignUpRequest;
import com.fly.delivery.dao.response.JwtAuthenticationResponse;
import com.fly.delivery.dao.response.SignUpResponse;
import com.fly.delivery.service.AdminService;
import com.fly.delivery.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/comptes")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://102.211.210.49:4200","http://www.flydelivery.com.tn:4200"}, allowedHeaders = "*")
public class AdminController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AdminService adminService;
    @GetMapping
    public ResponseEntity<Collection<SignUpResponse>> getAllClientAccount() {
        return ResponseEntity.ok(adminService.getAllClientAccount());
    }
    @GetMapping("/{email}")
    public ResponseEntity<SignUpResponse> getClientByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(adminService.getClientAccountByEmail(email));
    }
    @PostMapping("addClientAccount")
    public ResponseEntity<JwtAuthenticationResponse> addClientAccount(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signupForUser(request));
    }
}
