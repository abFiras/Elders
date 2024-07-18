package com.example.lastpi.Entity;

import com.example.lastpi.Enum.ServiceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Services implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idService;
    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
    private double price;
    @JsonInclude
    @ManyToMany(mappedBy = "services")
    private Set<User> users = new HashSet<>();
}
