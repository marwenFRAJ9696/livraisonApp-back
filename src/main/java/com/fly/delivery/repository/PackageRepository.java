package com.fly.delivery.repository;

import com.fly.delivery.entities.Package;
import com.fly.delivery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Integer> {
    // Since email is unique, we'll find users by email
    Optional<Package> findTopByOrderByIdDesc();
}
