package com.example.lastpi.ServiceIMp;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.util.Properties;

@Service
public class EmailService1 {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    public void sendPasswordResetEmail(String to, String resetUrl, String emailBody) {
        Properties props = new Properties();
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.port", mailPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUsername, mailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailUsername));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Password Reset");
            message.setText("Dear User,\n\n"
                    + "Please click the link below to reset your password:\n\n"
                    + resetUrl);

            Transport.send(message);
            System.out.println("Password reset email sent successfully to " + to);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending password reset email", e);
        }
    }
}
