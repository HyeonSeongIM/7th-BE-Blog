package com.leets.blog.dto.request;

import com.leets.blog.entity.enums.ReportReason;
import jakarta.validation.constraints.NotNull;

public record AddReportRequest(

        // [FIX] 기존: targetType, targetId, reportReason, reportId 필드 혼재 + setter 호출로 컴파일 에러.
        // record는 불변이므로 Controller에서 경로 변수(postId/commentId)로 받은 targetType/targetId는
        // 서비스 메서드 파라미터로 직접 전달하는 방식으로 분리.
        // reportId → reporterId로 네이밍 수정 (신고자 ID가 맞는 의도)
        @NotNull(message = "신고자 ID는 필수입니다.")
        Long reporterId,

        @NotNull(message = "신고 사유는 필수입니다.")
        ReportReason reason
) {}