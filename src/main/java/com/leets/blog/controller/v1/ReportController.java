package com.leets.blog.controller.v1;

import com.leets.blog.dto.request.AddReportRequest;
import com.leets.blog.dto.response.ReportResponse;
import com.leets.blog.entity.enums.ReportTargetType;
import com.leets.blog.service.ReportService;
import com.leets.blog.support.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * POST /api/v1/posts/{postId}/reports
     * 게시물 신고
     */
    @PostMapping("/api/v1/posts/{postId}/reports")
    public ApiResponse<ReportResponse> reportPost(
            @PathVariable Long postId,
            @RequestBody @Valid AddReportRequest request
    ) {
        // [FIX] record는 불변이라 setter 불가 → targetType/targetId를 서비스 파라미터로 직접 전달
        return ApiResponse.success(reportService.report(request, ReportTargetType.POST, postId));
    }

    /**
     * POST /api/v1/comments/{commentId}/reports
     * 댓글 신고
     */
    @PostMapping("/api/v1/comments/{commentId}/reports")
    public ApiResponse<ReportResponse> reportComment(
            @PathVariable Long commentId,
            @RequestBody @Valid AddReportRequest request
    ) {
        return ApiResponse.success(reportService.report(request, ReportTargetType.COMMENT, commentId));
    }

    /**
     * PATCH /api/v1/reports/{reportId}/resolve
     * 신고 처리 완료: PENDING → RESOLVED (관리자용)
     */
    @PatchMapping("/api/v1/reports/{reportId}/resolve")
    public ApiResponse<ReportResponse> resolveReport(@PathVariable Long reportId) {
        return ApiResponse.success(reportService.resolveReport(reportId));
    }
}