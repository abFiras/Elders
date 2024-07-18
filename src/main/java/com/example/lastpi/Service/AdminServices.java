package com.example.lastpi.Service;




import com.example.lastpi.Entity.Role;
import com.example.lastpi.Entity.Services;
import com.example.lastpi.Entity.User;
import com.example.lastpi.Enum.ServiceType;
import com.example.lastpi.Enum.Status;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    void changePassword(Long userId, String currentPassword, String newPassword) throws Exception;
    public User addUserWithRoles(String username, String email, String password, Set<String> roleNames,
                                 String firstName, String lastName, String cin, String phone, String gender) ;

    public User findByEmail(String email) ;
    void addServiceToUser( Long userId,ServiceType services) throws Exception;
    public int calculateTotalCostForUser(User user) ;
    public User handleImageFileUpload(MultipartFile fileImage, String id) ;




    }

