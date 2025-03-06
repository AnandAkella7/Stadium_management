package org.stadium.userservice.entity;

import jakarta.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.stadium.commonservice.authorization.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity

@Table( name ="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column( unique = true, nullable = false)
    @NotBlank(message =" Username is required")
    private String username;


    @Column( unique = true, nullable = false)
    @Email(message = "Invalid email format")
    @NotBlank(message =" Email is required")
    private String email;

    @JsonIgnore
    @Column( unique = true, nullable = false)
    private String password;

    @Column(name ="created_dT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column( name="user_role", nullable = false, updatable = false)
    private UserRole userRole;

}


