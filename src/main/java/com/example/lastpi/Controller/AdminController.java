package com.example.lastpi.Controller;



import com.example.lastpi.DTO.AddUserRequest;
import com.example.lastpi.Entity.ChangePasswordRequest;
import com.example.lastpi.Entity.Role;
import com.example.lastpi.Entity.Services;
import com.example.lastpi.Entity.User;
import com.example.lastpi.Enum.ServiceType;
import com.example.lastpi.Repo.UserRepo;
import com.example.lastpi.Service.AdminServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Tag(name = "Admin")

public class AdminController {
    @Autowired
    AdminServices adminServices;
    @Autowired
    UserRepo userRepository;

    @Operation(description = "getAllUsers")
    @GetMapping(path = "/getAllUsers")
    List<User> getAllUsers() {
        return adminServices.getall();
    }

    @Operation(description = "add user")
    @PostMapping(path = "/updateUser/{id}")
    void UpdatUser(@PathVariable Long id, @RequestBody String role) {
        adminServices.UpdateROle(id, role);
    }

    @Operation(description = "getAllRole")
    @GetMapping(path = "/getAllRole")
    List<Role> getAllRole() {
        return adminServices.getAllROles();

    }

    @PutMapping("/UpdateUser/{id}")
    public User updateUserById(@PathVariable("id") long id, @RequestBody User updatedUser) {
        return adminServices.updateUserById(id, updatedUser);
    }

    @PostMapping("/AddUser")
    public User AddUser(@RequestBody User user) {
        return adminServices.AddUser(user);
    }

    @PutMapping("/UpdateUserr")
    public User UpdateUser(@RequestBody User user) {
        return adminServices.AddUser(user);
    }



    @GetMapping("/FindAllUsers")
    public List<User> retrieveAllUsers() {
        return adminServices.retrieveAllUsers();
    }

    @GetMapping("/FindUserId/{id}")
    public Optional<User> retrieveUser(@PathVariable("id") long id) {
        return adminServices.retrieveUser(id);
    }

    @DeleteMapping("/DeleteUser/{id}")
    public void removeUser(@PathVariable("id") long id) {
        adminServices.removeUser(id);
    }

    @GetMapping("/FindConnectedUsers")
    public List<User> retrieveAllConnectedUsers() {
        return adminServices.retrieveAllConnectedUsers();
    }

    @GetMapping("/FindDisconnectedUsers")
    public List<User> retrieveAllDisconnectedUsers() {
        return adminServices.retrieveAllDisconnectedUsers();
    }

    @GetMapping("/getttuser/id")
    public long getUserIdFromUsername(@RequestParam String username){
        return adminServices.getUserIdFromUsername(username);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        try {
            adminServices.changePassword(request.getUserId(), request.getCurrentPassword(), request.getNewPassword());
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addUserWithRoles")
    public ResponseEntity<?> addUserWithRoles(@Valid @RequestBody AddUserRequest addUserRequest) {
        Set<String> roleNames = addUserRequest.getRoles(); // Obtenez les noms de rôles
        adminServices.addUserWithRoles(
                addUserRequest.getUsername(),
                addUserRequest.getEmail(),
                addUserRequest.getPassword(),
                new HashSet<>(roleNames), // Convertir la liste en ensemble
                addUserRequest.getFirstName(),
                addUserRequest.getLasteName(),
                addUserRequest.getCin(),
                addUserRequest.getPhone(),
                addUserRequest.getGender()
        );
        return ResponseEntity.ok("User added successfully with roles: " + roleNames);
    }
    @GetMapping("/users/addServices/{userId}/{services}")
    public String addServicesToElder(@PathVariable("userId") Long userId,@PathVariable("services") ServiceType services) throws Exception {
        adminServices.addServiceToUser(userId,services);
        return "Services added successfully to the elder user with ID: " + services;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "Requestor-Type", exposedHeaders = "X-Get-Header")
    @PostMapping("/dashboard/clubs/uploadImage/{id}")
    public User handleImageFileUpload(@RequestParam("fileImage") MultipartFile fileImage, @PathVariable String id) {
        return adminServices.handleImageFileUpload(fileImage, id);
    }


}
