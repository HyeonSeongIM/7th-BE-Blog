package com.leets.blog.dto.request;

import com.leets.blog.entity.enums.ReportReason;
import com.leets.blog.entity.enums.ReportTargetType;

public record AddReportRequest(
        ReportTargetType targetType,
        Long targetId,
        ReportReason reportReason,
        Long reportId
) {
}
