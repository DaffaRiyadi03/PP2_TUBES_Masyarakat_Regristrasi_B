package tubes.ewaste.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class MailService {

    // Kirim OTP ke email
    public void sendOtpEmail(String recipient, String otp) throws MessagingException {
        String senderEmail = "retrogamea00@gmail.com"; 
        String senderPassword = "aenc uihf hbka ycfl"; // Gantilah ini dengan cara yang lebih aman, misalnya menggunakan variabel lingkungan atau vault

        // Setup properties untuk session email
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // Port untuk TLS

        // Membuat session email
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Membuat pesan email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Your OTP for Registration");
            message.setText("Your OTP code is: " + otp);  // Pesan berisi OTP

            // Kirim email
            Transport.send(message);

            System.out.println("OTP sent successfully to " + recipient); // Log untuk keberhasilan
        } catch (MessagingException e) {
            e.printStackTrace();  // Menangani exception dan mencetak stack trace untuk debugging
            throw new MessagingException("Failed to send OTP email. Please check your email settings.");
        }
    }
}
