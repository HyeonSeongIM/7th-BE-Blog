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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // ── Domain Methods ─────────────────────────────────────────────────────────

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }

    public void increaseReportCount() {
        this.reportCount++;
        if (this.reportCount >= HIDE_THRESHOLD) {
            this.status = ContentStatus.HIDDEN;
        }
    }

    // [ADD] 관리자용 수동 상태 전이 — PostService.hidePost() / activatePost()에서 호출
    public void hide() {
        this.status = ContentStatus.HIDDEN;
    }

    public void activate() {
        this.status = ContentStatus.ACTIVE;
    }

    // [ADD] 채택된 댓글 존재 여부 — CommentService.acceptComment()에서 중복 채택 방지에 사용
    public boolean hasAcceptedComment() {
        return this.comments.stream().anyMatch(Comment::isAccepted);
    }

    public boolean isActive() { return this.status == ContentStatus.ACTIVE; }
    public boolean isHidden() { return this.status == ContentStatus.HIDDEN; }
}