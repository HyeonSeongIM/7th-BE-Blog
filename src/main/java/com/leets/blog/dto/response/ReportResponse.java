package com.leets.blog.dto.response;

import com.leets.blog.entity.enums.ReportReason;
import com.leets.blog.entity.enums.ReportTargetType;

import java.time.LocalDateTime;

public record ReportResponse(
        Long id,
        Long reporterId,
        ReportTargetType targetType,
        Long targetId,
        ReportReason reason,
        LocalDateTime createdAt
) {
}
