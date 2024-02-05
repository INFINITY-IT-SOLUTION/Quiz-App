package com.QuizApp.authservice.Controllers;

import com.QuizApp.authservice.Domains.PasswordResetToken;
import com.QuizApp.authservice.Domains.User;
import com.QuizApp.authservice.Repositories.PasswordResetTokenRepository;
import com.QuizApp.authservice.Repositories.UserRepository;
import com.QuizApp.authservice.Requests.EmailRequest;
import com.QuizApp.authservice.Requests.PasswordRequest;
import com.QuizApp.authservice.Responses.EmailResponse;
import com.QuizApp.authservice.Responses.PasswordResponse;
import com.QuizApp.authservice.Responses.UserNotFoundResponse;
import com.QuizApp.authservice.Services.EmailService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping("/api/v1/password")
@RestController
public class PasswordForgetController {


    private final UserRepository userRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;


    public PasswordForgetController(UserRepository userRepository,
                                    PasswordResetTokenRepository passwordResetTokenRepository,
                                    PasswordEncoder passwordEncoder,
                                    EmailService emailService){
        this.userRepository= userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmailResponse.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserNotFoundResponse.class))
            }),
            @ApiResponse(responseCode = "400", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PasswordResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true, schema = @Schema(type = "string"))
    @PostMapping("/send-password")
    public ResponseEntity<?> passwordResetEmail(@RequestBody EmailRequest request) throws MessagingException {
        if(!userRepository.existsByEmail(request.email())){
            return ResponseEntity.status(404).body(new UserNotFoundResponse("User not found by this email."));
        }
        UserDetails user = userRepository.findUserByEmail(request.email());

        if(passwordResetTokenRepository.existsByUser((User) user)){
            return ResponseEntity.badRequest().body(new PasswordResponse("An email has already been sent."));
        }

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUser((User) user);
        passwordResetTokenRepository.save(passwordResetToken);

        emailService.sendResetPasswordEmail((User) user);

        return ResponseEntity.ok(new EmailResponse("Email send."));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PasswordResponse.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserNotFoundResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true, schema = @Schema(type = "string"))
    @Transactional
    @PostMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestParam(name = "token") String token, @RequestBody PasswordRequest request) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

        if (passwordResetToken != null) {
            User user = passwordResetToken.getUser();

            if (user.isEnabled()) {
                String newPassword = request.newPassword();
                String confirmPassword = request.confirmPassword();

                // Vérifier si les nouveaux mots de passe correspondent
                if (newPassword.equals(confirmPassword)) {
                    // Mettez à jour le mot de passe uniquement si les nouveaux mots de passe correspondent
                    user.setPassword(passwordEncoder.encode(newPassword));
                    passwordResetToken.setUser(null);
                    passwordResetTokenRepository.delete(passwordResetToken);
                    userRepository.save(user);

                    return ResponseEntity.ok(new PasswordResponse("Password has been changed."));
                } else {
                    return ResponseEntity.status(400).body("New password and confirm password do not match.");
                }
            } else {
                return ResponseEntity.status(404).body(new UserNotFoundResponse("User not found."));
            }
        }

        return ResponseEntity.notFound().build();
    }
}