package com.fly.delivery.service;


import com.fly.delivery.dao.request.PackageRequest;
import com.fly.delivery.dao.response.ColisResponse;
import com.fly.delivery.dao.response.PackageResponse;
import com.fly.delivery.dao.response.StatusResponse;

import java.util.Collection;

public interface PackageService {
   ColisResponse addPackage(PackageRequest request);

   Collection<PackageResponse> getPackageByClient(String email);

   StatusResponse changeStatus(Integer id,String status);

   Collection<PackageResponse> getAllPackageByCreatedDate();

   PackageResponse getPackageById(Integer id);

   PackageResponse updatePackageById(Integer id, PackageRequest packageRequest);
}
