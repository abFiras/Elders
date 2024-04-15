package com.example.lastpi.Controller;



import com.example.lastpi.Entity.Role;
import com.example.lastpi.Entity.User;
import com.example.lastpi.Repo.UserRepo;
import com.example.lastpi.Service.AdminServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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


}
