package com.leets.blog.entity;

import com.leets.blog.entity.enums.ContentStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
public class Post {

    private static final int HIDE_THRESHOLD = 5;

    public Post() {}

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = ContentStatus.ACTIVE;
        this.reportCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String imageUrl;

    private boolean liked;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentStatus status;

    @Column(nullable = false)
    private int reportCount;

    // Post는 하나의 User와 연결됨 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Post는 여러 개의 Comment와 연결됨 (1:N)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void increaseReportCount() {
        this.reportCount++;
        if (this.reportCount >= HIDE_THRESHOLD) {
            this.status = ContentStatus.HIDDEN;
        }
    }

    public boolean isActive() {
        return this.status == ContentStatus.ACTIVE;
    }

    public boolean isHidden() {
        return this.status == ContentStatus.HIDDEN;
    }
}