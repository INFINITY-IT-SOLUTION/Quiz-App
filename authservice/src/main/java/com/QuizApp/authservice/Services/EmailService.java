package com.QuizApp.authservice.Services;

import com.QuizApp.authservice.Domains.PasswordResetToken;
import com.QuizApp.authservice.Domains.User;
import com.QuizApp.authservice.Domains.VerificationToken;
import com.QuizApp.authservice.Repositories.PasswordResetTokenRepository;
import com.QuizApp.authservice.Repositories.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private final VerificationTokenRepository verificationTokenRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender javaMailSender;

    public EmailService(VerificationTokenRepository verificationTokenRepository, JavaMailSender javaMailSender, PasswordResetTokenRepository passwordResetTokenRepository){
        this.verificationTokenRepository = verificationTokenRepository;
        this.javaMailSender = javaMailSender;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
    public void sendVerifyEmail(User user) throws MessagingException {
        VerificationToken verificationToken = verificationTokenRepository.findByUser(user);

        if (verificationToken != null) {
            // Générer un token à quatre chiffres
            String token = generateFourDigitToken();

            // Mettre à jour le token dans la base de données
            verificationToken.setToken(token);
            verificationTokenRepository.save(verificationToken);

            // Construire le modèle d'e-mail avec le nouveau token
            String template =
                    "<h3>Email verification</h3>" +
                            "<p>Thank you for signing up to QuizApp. Please enter the following verification code to verify your email address:</p>" +
                            "<div style='font-size: 24px; font-weight: bold; padding: 10px; background-color: #f2f2f2; border-radius: 5px;'>" + token + "</div>";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Email Verification");
            helper.setText(template, true);
            javaMailSender.send(message);
        }
    }

    private String generateFourDigitToken() {
        Random random = new Random();
        int token = 1000 + random.nextInt(9000); // Générer un nombre aléatoire entre 1000 et 9999
        return String.valueOf(token);
    }

    public void sendResetPasswordEmail(User user) throws MessagingException {
        PasswordResetToken verificationToken = passwordResetTokenRepository.findByUser(user);

        if (verificationToken != null) {
            // Générez un code aléatoire à 4 chiffres
            String verificationCode = generateVerificationCode();

            // Enregistrez le code dans votre base de données ou dans un autre endroit si nécessaire
            verificationToken.setToken(verificationCode);
            passwordResetTokenRepository.save(verificationToken);

            // Incluez le code dans le corps du courriel
            String template =
                    "<h3>Password reset</h3> \n <p> "+
                      "<p>Your verification code is:</p>" +
                    "<div style='font-size: 24px; font-weight: bold; padding: 10px; background-color: #f2f2f2; border-radius: 5px;'>" + verificationCode +  "</div>";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Password reset");
            helper.setText(template, true);
            javaMailSender.send(message);
        }
    }

    private String generateVerificationCode() {
        // Générez un code aléatoire à 4 chiffres
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        return String.valueOf(code);
    }
}
