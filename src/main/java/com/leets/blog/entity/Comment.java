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
        this.liked = false;
        this.accepted = false;
        this.createAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean liked;

    // [ADD] 채택 여부
    @Column(nullable = false)
    private boolean accepted;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentStatus status;

    @Column(nullable = false)
    private int reportCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // ── Getters ────────────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public String getContent() { return content; }
    public boolean isLiked() { return liked; }
    public boolean isAccepted() { return accepted; }
    public LocalDateTime getCreateAt() { return createAt; }
    public ContentStatus getStatus() { return status; }
    public int getReportCount() { return reportCount; }
    public User getUser() { return user; }
    public Post getPost() { return post; }

    // ── Domain Methods ─────────────────────────────────────────────────────────

    // [ADD] CommentManager.add()에서 호출
    public void assignPost(Post post) {
        this.post = post;
    }

    // [ADD] CommentManager.add()에서 호출
    public void assignUser(User user) {
        this.user = user;
    }

    // [ADD] CommentManager.update()에서 호출
    public void updateContent(String newContent) {
        this.content = newContent;
    }

    // [ADD] CommentManager.delete()에서 호출 — 물리 삭제 대신 상태 전이
    public void softDelete() {
        this.status = ContentStatus.HIDDEN;
    }

    // [ADD] 채택 처리 — CommentService.acceptComment()에서 호출
    public void accept() {
        this.accepted = true;
    }

    public void increaseReportCount() {
        this.reportCount++;
        if (this.reportCount >= HIDE_THRESHOLD) {
            this.status = ContentStatus.HIDDEN;
        }
    }

    public boolean isActive() { return this.status == ContentStatus.ACTIVE; }
    public boolean isHidden() { return this.status == ContentStatus.HIDDEN; }
}