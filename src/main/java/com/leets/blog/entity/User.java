package com.leets.blog.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    public User() {}

    public User(String name, String email, String phoneNumber, String nickname, String image, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.image = image;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    private String nickname;

    private String image;

    @Column(nullable = false)
    private String password;

    // User는 여러 개의 Post와 연결됨 (1:N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    // User는 여러 개의 Comment와 연결됨 (1:N)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Long getId() {
        return this.id;
    }
}