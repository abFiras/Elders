package com.example.lastpi.ServiceIMp;


import com.example.lastpi.Entity.Role;
import com.example.lastpi.Entity.Services;
import com.example.lastpi.Entity.User;
import com.example.lastpi.Enum.ERole;
import com.example.lastpi.Enum.Gender;
import com.example.lastpi.Enum.ServiceType;
import com.example.lastpi.Enum.Status;
import com.example.lastpi.Repo.RoleRepo;
import com.example.lastpi.Repo.ServicesRepository;
import com.example.lastpi.Repo.UserRepo;
import com.example.lastpi.Service.AdminServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminServicesImpl implements AdminServices {
    @Autowired
    UserRepo userRepository;
    @Autowired
    RoleRepo roleRepository;
    @Autowired
    ServicesRepository servicesRepository;
    @Autowired
    FileStorageService fileStorageService;
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
    @Transactional
    @Override

    public User AddUser(User user){

        return userRepository.save(user);}
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


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override

    public void changePassword(Long userId, String currentPassword, String newPassword) throws Exception {
        User user = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() -> new Exception("User not found"));

        // Check if the current password matches
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new Exception("Current password is incorrect");
        }

        // Encode the new password before saving
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override

    public User addUserWithRoles(String username, String email, String password, Set<String> roleNames,
                                 String firstName, String lastName, String cin, String phone, String gender) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        User user = new User(username, email, passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLasteName(lastName);
        user.setCin(cin);
        user.setPhone(phone);
        user.setGender(Gender.valueOf(gender));

        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            ERole eRole = ERole.valueOf(roleName.toUpperCase());
            Role role = roleRepository.findByName(eRole)
                    .orElseThrow(() -> new IllegalArgumentException("Role '" + roleName + "' not found."));
            roles.add(role);
        }

        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return  userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public void addServiceToUser(Long userId,ServiceType serviceType) throws Exception {
        // Récupérer l'utilisateur depuis la base de données
        User user = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() -> new Exception("User not found"));

        // Récupérer le service depuis la base de données

        Services services=servicesRepository.findByServiceType(serviceType);
        // Ajouter l'utilisateur au service
        services.getUsers().add(user);
        user.getServices().add(services);

        // Enregistrer les modifications apportées au service
        servicesRepository.save(services);
    }



   /* public void addServicesToElder(Long userId) {
        User elderUser = userRepository.findById(String.valueOf(userId)).orElse(null);
        if (elderUser != null && hasElderRole(elderUser.getRoles())) {
            addServiceToUser(elderUser, ServiceType.FORUM);
            addServiceToUser(elderUser, ServiceType.CHAT);
            addServiceToUser(elderUser, ServiceType.NOTIFICATIONS);
            addServiceToUser(elderUser, ServiceType.EVENTS);
            addServiceToUser(elderUser, ServiceType.DOCTOR_SERVICES);
            addServiceToUser(elderUser, ServiceType.NURSE_SERVICES);

            int totalCost = calculateTotalCostForUser(elderUser);
            System.out.println("Total cost for elder user: " + totalCost + " euro");
        }
    }*/
    private boolean hasElderRole(Set<Role> roles) {
        for (Role role : roles) {
            if (role.getName() == ERole.ELDER) {
                return true;
            }
        }
        return false;
    }
    @Override
    public int calculateTotalCostForUser(User user) {
        int totalCost = 0;
        Set<ServiceType> selectedServices = user.getSelectedServices();
        for (ServiceType serviceType : selectedServices) {
            switch (serviceType) {
                case CHAT:
                case FORUM:
                case NOTIFICATIONS:
                    totalCost += 30;
                    break;
                case EVENTS:
                    totalCost += 50;
                    break;
                case DOCTOR_SERVICES:
                    totalCost += 100;
                    break;
                case NURSE_SERVICES:
                    totalCost += 80;
                    break;
                default:
                    // Handle unknown service types
                    break;
            }
        }
        return totalCost;
    }


    @Override
    public User handleImageFileUpload(MultipartFile fileImage, String id) {
        if (fileImage.isEmpty()) {
            return null;
        }
        String fileName = fileStorageService.storeFile(fileImage);
        User user = userRepository.findIdByUsername(id);
        user.setImgUrl(fileName);
        return userRepository.save(user);
    }

}