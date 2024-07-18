package com.example.lastpi.ServiceIMp;

import com.example.lastpi.Entity.PasswordResetToken;
import com.example.lastpi.Entity.User;
import com.example.lastpi.Repo.PasswordResetTokenRepository;
import com.example.lastpi.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PasswordResetService {
    private final EmailService1 emailService;


    private final UserRepo userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetService(EmailService1 emailService, UserRepo userRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public String generatePasswordResetToken(User userId) throws ChangeSetPersister.NotFoundException {
        // Retrieve the user from the repository
        User user = userRepository.findById(String.valueOf(userId))
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Generate a random token (you can use a utility method for this)
        String token = generateRandomToken();

        // Create a PasswordResetToken
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);

        // Save the PasswordResetToken
        passwordResetTokenRepository.save(passwordResetToken);
        return token;
    }

    // Utility method to generate a random token

    private String generateRandomToken() {
        byte[] randomBytes = new byte[32]; // Adjust the length of the byte array as needed
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public boolean sendPasswordResetEmail(User user, String token) {
        String resetUrl = "http://localhost:4200/reset-password?token=" + token;
        String emailBody = "Click the link below to reset your password:\n" + resetUrl;

        // Send email
        try {
            emailService.sendPasswordResetEmail(user.getEmail(), "Password Reset", emailBody);
            return true; // Email sent successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Failed to send email
        }
    }
}

