package com.example.lastpi.Controller;


import com.example.lastpi.DTO.LoginDto;
import com.example.lastpi.DTO.SignupDto;
import com.example.lastpi.Entity.Role;
import com.example.lastpi.Entity.User;
import com.example.lastpi.Enum.ERole;
import com.example.lastpi.Enum.Gender;
import com.example.lastpi.Repo.RoleRepo;
import com.example.lastpi.Repo.UserRepo;
import com.example.lastpi.Response.JwtResponse;
import com.example.lastpi.Response.MessageResponse;
import com.example.lastpi.Security.JWT.JwtUtils;
import com.example.lastpi.Security.Service.UserDetailsImpl;
import com.example.lastpi.ServiceIMp.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepository;

    @Autowired
    RoleRepo roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    EmailService emailService;


    @Operation(description = "signin")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println("jwwwwtt  "+jwt);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        System.out.println(userDetails.getUsername());
        System.out.println("jetttonnn");
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                userDetails.getFirstname(),
                userDetails.getLastename(),
                userDetails.getCin(),
                userDetails.getPhone(),
                userDetails.getGender(),
                userDetails.getStatus()));

    }


    private void sendEmailNotification(String recipientEmail) {
        try {
            String subject = "New user registration";
            String content = "thanks for verifying your email\n\n";

            emailService.sendEmail(recipientEmail, subject, content);
        } catch (Exception e) {
            e.printStackTrace(); // Handle email sending exception
        }
    }

    @Operation(description = "signup")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto signupDto) {
        if (userRepository.existsByUsername(signupDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }


        // Création du compte utilisateur
        User user = new User(signupDto.getUsername(),
                signupDto.getEmail(),
                encoder.encode(signupDto.getPassword()));
        user.setFirstName(signupDto.getFirstname());
        user.setLasteName(signupDto.getLastename());
        user.setCin(signupDto.getCin());
        user.setPhone(signupDto.getPhone());
        user.setGender(Gender.valueOf(signupDto.getGender()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ELDER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
        sendEmailNotification(user.getEmail());
        // Génération et enregistrement du token de vérification (implémentez cette logique)
        // String verificationToken = UUID.randomUUID().toString();
        // Enregistrez le token ici

        // Envoi de l'email de vérification
        // String verificationUrl = "http://localhost:8080/api/auth/confirm-account?token=" + verificationToken;
        // emailService.sendEmail(user.getEmail(), "Vérification de votre compte",
        //       "Pour activer votre compte, veuillez cliquer sur le lien suivant: " + verificationUrl);

        return ResponseEntity.ok(new MessageResponse("Inscription réussie. Veuillez vérifier votre email pour activer votre compte."));

    }}