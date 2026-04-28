package com.leets.blog.entity;

import com.leets.blog.entity.enums.ReportReason;
import com.leets.blog.entity.enums.ReportTargetType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "report",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"reporter_id", "target_type", "target_id"})
        }
)
public class Report {

    public Report() {}

    public Report(User reporter, ReportTargetType targetType, Long targetId, ReportReason reason) {
        this.reporter = reporter;
        this.targetType = targetType;
        this.targetId = targetId;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신고한 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    // 신고 대상 타입 (POST / COMMENT)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportTargetType targetType;

    // 신고 대상 ID (postId or commentId)
    @Column(nullable = false)
    private Long targetId;

    // 신고 사유
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public User getReporter() {
        return reporter;
    }

    public ReportTargetType getTargetType() {
        return targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public ReportReason getReason() {
        return reason;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}