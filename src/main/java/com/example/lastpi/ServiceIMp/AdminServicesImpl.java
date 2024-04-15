package com.example.lastpi.ServiceIMp;


import com.example.lastpi.Entity.Role;
import com.example.lastpi.Entity.User;
import com.example.lastpi.Enum.Status;
import com.example.lastpi.Repo.RoleRepo;
import com.example.lastpi.Repo.UserRepo;
import com.example.lastpi.Service.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServicesImpl implements AdminServices {
    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepo roleRepository;
    @Override
    public List<User> getall() {
        return userRepository.findAll();
    }

    @Override
    public void UpdateROle(Long id, String role) {

    }

    @Override
    public long getUserIdFromUsername(String username) {
        User user = userRepository.findIdByUsername(username);
        if (user != null) {
            return user.getId();
        } else {
            // Handle case when user is not found
            return -1; // Or throw an exception, depending on your requirements
        }

    }

    @Override
    public List<Role> getAllROles() {
        return roleRepository.findAll();
    }

    public User AddUser(User user){return userRepository.save(user);}
    public List <User> retrieveAllUsers(){return userRepository.findAll();}
    public void removeUser(Long id){userRepository.deleteById(String.valueOf(id));}
    public Optional<User> retrieveUser(long id){return userRepository.findById(String.valueOf(id));}
    public List <User> retrieveAllConnectedUsers(){
        return userRepository.findUsersByStatus(Status.ONLINE);
    }
    public List <User> retrieveAllDisconnectedUsers(){
        return userRepository.findUsersByStatus(Status.OFFLINE);
    }



    public User updateUserById(long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(String.valueOf(id));
        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setRoles(updatedUser.getRoles());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setLasteName(updatedUser.getLasteName());
            // Update other fields as needed
            return userRepository.save(existingUser);
        } else {
            // Handle user not found
            return null;
        }
    }


}