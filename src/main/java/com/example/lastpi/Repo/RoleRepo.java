package com.example.lastpi.Repo;


import com.example.lastpi.Entity.Role;
import com.example.lastpi.Enum.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,String> {
    Optional<Role> findByName (ERole name);
    boolean existsByName(ERole r1);
}
