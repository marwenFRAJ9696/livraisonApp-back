package com.fly.delivery.controller;

import com.fly.delivery.dao.request.PackageRequest;
import com.fly.delivery.dao.response.ColisResponse;
import com.fly.delivery.dao.response.PackageResponse;
import com.fly.delivery.dao.response.StatusResponse;
import com.fly.delivery.service.AdminService;
import com.fly.delivery.service.AuthenticationService;
import com.fly.delivery.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/packages")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://102.211.210.49:4200","http://www.flydelivery.com.tn:4200","http://www.flydelivery.com.tn"}, allowedHeaders = "*")
public class PackageController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private AdminService adminService;
    @GetMapping({"{email}"})
    public ResponseEntity<Collection<PackageResponse>> getPackageByClient(@PathVariable String email) {
        return ResponseEntity.ok(packageService.getPackageByClient(email));
    }
    @PostMapping("addPackage")
    public ResponseEntity<ColisResponse> addPackage(@RequestBody PackageRequest request) {
        return ResponseEntity.ok(packageService.addPackage(request));
    }
    @PostMapping("changeStatus/{id}")
    public ResponseEntity<StatusResponse> changeStatus(@PathVariable Integer id,@RequestParam("status") String status) {
        return ResponseEntity.ok(packageService.changeStatus(id,status));
    }
    @GetMapping({"/createdDate"})
    public ResponseEntity<Collection<PackageResponse>> getAllPackageByCreatedDate() {
        return ResponseEntity.ok(packageService.getAllPackageByCreatedDate());
    }
}
