package com.leets.blog.controller.v1;

import com.leets.blog.dto.request.AddReportRequest;
import com.leets.blog.dto.response.ReportResponse;
import com.leets.blog.service.ReportService;
import com.leets.blog.support.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ApiResponse<ReportResponse> report(@RequestBody @Valid AddReportRequest request) {
        ReportResponse result = reportService.report(request);
        return ApiResponse.success(result);
    }
}