package com.QuizApp.authservice.Controllers;

import com.QuizApp.authservice.DTO.UserDTO;
import com.QuizApp.authservice.Domains.User;
import com.QuizApp.authservice.Repositories.UserRepository;
import com.QuizApp.authservice.Requests.UserRequest;
import com.QuizApp.authservice.Responses.UserNotFoundResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true, schema = @Schema(type = "string"))
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll().stream().map(UserDTO::new).toList();
        return ResponseEntity.ok(users);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserNotFoundResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{email}")
    public ResponseEntity<?> getOneUser(@PathVariable String email){
        if(!userRepository.existsByEmail(email)){
            return ResponseEntity.status(404).body(new UserNotFoundResponse("User not found."));
        }
        return ResponseEntity.ok(new UserDTO(userRepository.getReferenceByEmail(email)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserNotFoundResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true, schema = @Schema(type = "string"))
    @PutMapping("/{email}")
    public ResponseEntity<?> updateUser(@PathVariable String email, UserRequest userDTO){
        if(!userRepository.existsByEmail(email)){
            return ResponseEntity.status(404).body(new UserNotFoundResponse("User not found."));
        }
        User user = userRepository.getReferenceByEmail(email);
        user.setUsername(userDTO.username());
        user.setName(userDTO.name());
        user.setDaten(userDTO.daten());
        user.setPays(userDTO.pays());
        user.setPhone(userDTO.phone());
        user.setEmail(userDTO.email());

        return ResponseEntity.ok(new UserDTO(userRepository.save(user)));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserNotFoundResponse.class))
            })
    })
    @Parameter(in = ParameterIn.HEADER, name = "X-Authorization", required = true, schema = @Schema(type = "string"))
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        if(!userRepository.existsByEmail(email)){
            return ResponseEntity.status(404).body(new UserNotFoundResponse("User not found."));
        }
        userRepository.delete(userRepository.getReferenceByEmail(email));
        return ResponseEntity.noContent().build();
    }

}
