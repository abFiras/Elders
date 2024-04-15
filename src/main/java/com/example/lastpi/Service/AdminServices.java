package com.example.lastpi.Service;




import com.example.lastpi.Entity.Role;
import com.example.lastpi.Entity.User;
import com.example.lastpi.Enum.Status;

import java.util.List;
import java.util.Optional;

public interface AdminServices {
    List<User> getall();
    void UpdateROle(Long id,String role);
    List<Role> getAllROles();



    public User AddUser(User user);
    public List <User> retrieveAllUsers();
    public void removeUser(Long id);
    public Optional<User> retrieveUser(long id);
    public List <User> retrieveAllConnectedUsers();
    public List <User> retrieveAllDisconnectedUsers();



    public User updateUserById(long id, User updatedUser);
    long getUserIdFromUsername(String username);
}
