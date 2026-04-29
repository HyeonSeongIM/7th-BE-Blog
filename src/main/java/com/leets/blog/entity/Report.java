package com.leets.blog.entity;

import com.leets.blog.entity.enums.ReportReason;
import com.leets.blog.entity.enums.ReportStatus;
import com.leets.blog.entity.enums.ReportTargetType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "report"
)
public class Report {

    public Report() {
    }

    public Report(User reporter, ReportTargetType targetType, Long targetId, ReportReason reason) {
        this.reporter = reporter;
        this.targetType = targetType;
        this.targetId = targetId;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
        // [ADD] 생성 시 기본 상태는 PENDING
        this.status = ReportStatus.PENDING;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportTargetType targetType;

    @Column(nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason;

    // [ADD] 신고 처리 상태 (PENDING → RESOLVED)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // ── Getters ────────────────────────────────────────────────────────────────

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

    public ReportStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // ── Domain Methods ─────────────────────────────────────────────────────────

    // [ADD] PENDING → RESOLVED 상태 전이 — ReportService.resolveReport()에서 호출
    // 엔티티 계층이 예외 클래스에 의존하지 않도록 IllegalStateException 사용.
    // 호출 측(ReportService)에서 필요 시 ReportException으로 감싸서 처리 가능.
    public void resolve() {
        if (this.status == ReportStatus.RESOLVED) {
            throw new IllegalStateException("REPORT_ALREADY_RESOLVED");
        }
        this.status = ReportStatus.RESOLVED;
    }

    public boolean isPending() {
        return this.status == ReportStatus.PENDING;
    }

    public boolean isResolved() {
        return this.status == ReportStatus.RESOLVED;
    }
}