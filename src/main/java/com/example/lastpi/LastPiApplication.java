package com.example.lastpi;

import com.example.lastpi.Entity.Role;
import com.example.lastpi.Enum.ERole;
import com.example.lastpi.Repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class LastPiApplication {
    @Autowired
    RoleRepo roleRepository;
    public static void main(String[] args) {
        SpringApplication.run(LastPiApplication.class, args);
    }
    @EventListener(ApplicationReadyEvent.class)
    @Bean
    CommandLineRunner start() {
        return args -> {

            for (ERole r1 : ERole.values()
            ) {

                if (!roleRepository.existsByName(r1)) {
                    Role r = new Role(r1);
                    roleRepository.save(r);
                }
            }

        };
    }

}
