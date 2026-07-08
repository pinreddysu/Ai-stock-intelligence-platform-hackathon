package com.ai_stock_intelligience.aiStockIntelligenceBackend.Models;
import static com.ai_stock_intelligience.aiStockIntelligenceBackend.Constants.UserConstants.regexPatternName;
import static com.ai_stock_intelligience.aiStockIntelligenceBackend.Constants.UserConstants.regexPatternEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="firstname", nullable = false)
    @NotBlank(message = "First Name is required")
    @Pattern(regexp = regexPatternName)
    private String firstName;

    @Column(name="lastname", nullable = false)
    @NotBlank(message = "Last Name is required")
    @Pattern(regexp = regexPatternName)
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email Address is required")
//    @Pattern(regexp = regexPatternEmail, message = "Email must be in format Text@emailproveder.extension")
    private String email;

    @Column(name="passwordhash", nullable = false)
    @NotBlank(message = "Password is required")
    private String passwordHash;

    @CreationTimestamp
    @Column(name = "createdat", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedat", nullable = true)
    private LocalDateTime updatedAt;

    public Users(Long id, String firstName, String lastName, String email,
                 String passwordHash, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
