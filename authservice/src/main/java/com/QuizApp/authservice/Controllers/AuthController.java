package com.QuizApp.authservice.Controllers;

import com.QuizApp.authservice.Domains.Authority;
import com.QuizApp.authservice.Domains.User;
import com.QuizApp.authservice.Domains.VerificationToken;
import com.QuizApp.authservice.Exceptions.CustomException;
import com.QuizApp.authservice.Repositories.AuthorityRepository;
import com.QuizApp.authservice.Repositories.UserRepository;
import com.QuizApp.authservice.Repositories.VerificationTokenRepository;
import com.QuizApp.authservice.Requests.SignInRequest;
import com.QuizApp.authservice.Requests.SignUpRequest;
import com.QuizApp.authservice.Responses.EmailExistResponse;
import com.QuizApp.authservice.Responses.SignUpResponse;
import com.QuizApp.authservice.Responses.UserNotFoundResponse;
import com.QuizApp.authservice.Security.JWT.JWTResponse;
import com.QuizApp.authservice.Security.JWT.JWTService;
import com.QuizApp.authservice.Services.EmailService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final VerificationTokenRepository verificationTokenRepository;
    private final EmailService emailService;
    private final JWTService jwtService;



    public AuthController(JWTService jwtService,
                          AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthorityRepository authorityRepository,
                          VerificationTokenRepository verificationTokenRepository,
                          EmailService emailService){
        this.jwtService = jwtService;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JWTResponse.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserNotFoundResponse.class))
            })
    })
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest request) throws CustomException {
        if(!userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.status(404).body(new UserNotFoundResponse("User not found by this email."));
        }
        Authentication token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        Authentication authUser = authenticationManager.authenticate(token);

        String jwt = jwtService.generateToken((User) authUser.getPrincipal());

        return ResponseEntity.ok(new JWTResponse(jwt));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SignUpResponse.class))
            }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EmailExistResponse.class))
            })
    })
    @PostMapping(value = "/sign-up", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> signUp(@ModelAttribute @Valid SignUpRequest request) throws MessagingException, IOException {
        if (userRepository.findUserByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body(new EmailExistResponse("This email already exists."));
        }
        Authority authority = authorityRepository.findById(2L)
                .orElseGet(() -> {
                    Authority newAuthority = new Authority();
                    newAuthority.setId(2L);
                    newAuthority.setName("USER");  // Définissez le nom approprié
                    return authorityRepository.save(newAuthority);
                });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setDaten(request.getDaten());
        user.setPays(request.getPays());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthority(authority);
        authority.getUsers().add(user);


        VerificationToken token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);

        verificationTokenRepository.save(token);

        emailService.sendVerifyEmail(user);

        return ResponseEntity.ok().body(new SignUpResponse(userRepository.save(user)));
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404")
    })
    @GetMapping("/email-verify")
    public ResponseEntity<?> verifyEmail(@RequestParam(name = "token") String token){
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken != null) {
            User user = verificationToken.getUser();
            if (user.getEmailVerifiedAt() == null) {
                user.setEmailVerifiedAt(LocalDate.now());
                verificationToken.setUser(null);
                verificationTokenRepository.delete(verificationToken);
                userRepository.save(user);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
