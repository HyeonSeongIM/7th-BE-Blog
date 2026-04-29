package com.leets.blog.dto.response;

import com.leets.blog.entity.enums.ReportReason;
import com.leets.blog.entity.enums.ReportStatus;
import com.leets.blog.entity.enums.ReportTargetType;

import java.time.LocalDateTime;

public record ReportResponse(
        Long id,
        Long reporterId,
        ReportTargetType targetType,
        Long targetId,
        ReportReason reason,
        // [ADD] 신고 처리 상태 — PENDING/RESOLVED, resolve API 결과 확인에 필요
        ReportStatus status,
        LocalDateTime createdAt
) {}