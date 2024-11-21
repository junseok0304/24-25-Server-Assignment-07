package com.example.jwtrefresh.Domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USER_PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_PHONE_NUMBER", nullable = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Column(name = "USER_NAME", nullable = true)
    private String name;

    @Column(name = "USER_PROFILE_URL", nullable = true)
    private String profileUrl;

    @Builder
    public User(String email, String password, String phoneNumber, Role role, String name, String profileUrl) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role != null ? role : Role.USER;
        this.name = name;
        this.profileUrl = profileUrl;
    }
}
