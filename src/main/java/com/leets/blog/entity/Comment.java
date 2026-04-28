package com.leets.blog.entity;

import com.leets.blog.entity.enums.ContentStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {

    private static final int HIDE_THRESHOLD = 5;

    public Comment() {}

    public Comment(String content) {
        this.content = content;
        this.status = ContentStatus.ACTIVE;
        this.reportCount = 0;
        this.createAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean liked;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentStatus status;

    @Column(nullable = false)
    private int reportCount;

    // Comment는 하나의 User와 연결됨 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // FK: userId
    private User user;

    // Comment는 하나의 Post에 속함 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id") // FK: postId
    private Post post;

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isLiked() {
        return liked;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public int getReportCount() {
        return reportCount;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
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