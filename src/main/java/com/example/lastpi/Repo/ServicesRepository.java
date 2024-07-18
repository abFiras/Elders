package com.example.lastpi.Repo;

import com.example.lastpi.Entity.Services;
import com.example.lastpi.Enum.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicesRepository extends JpaRepository<Services, Integer> {
    Services findByServiceType(ServiceType serviceType);
}