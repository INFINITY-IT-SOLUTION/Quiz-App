package com.QuizApp.authservice.DTO;

import com.QuizApp.authservice.Domains.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String email;

    private String pays;

    private String phone;

    private Boolean enabled;

    private String authority;

    private LocalDate emailVerifiedAt;


    private Date createdAt;

    public UserDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.pays = user.getPays();
        this.phone = user.getPhone();
        this.enabled = user.isEnabled();
        this.authority = user.getAuthority().getName();
        this.emailVerifiedAt = user.getEmailVerifiedAt();
        this.createdAt = user.getCreatedAt();
    }
}
