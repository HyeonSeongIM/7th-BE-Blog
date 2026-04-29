package com.leets.blog.service;

import com.leets.blog.dto.request.AddReportRequest;
import com.leets.blog.dto.response.ReportResponse;
import com.leets.blog.entity.Comment;
import com.leets.blog.entity.Post;
import com.leets.blog.entity.Report;
import com.leets.blog.entity.User;
import com.leets.blog.entity.enums.ReportReason;
import com.leets.blog.entity.enums.ReportTargetType;
import com.leets.blog.repository.ReportRepository;
import com.leets.blog.support.error.ErrorType;
import com.leets.blog.support.error.ReportException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private final UserFinder userFinder;
    private final ReportFinder reportFinder;
    private final ReportValidator reportValidator;
    private final ReportManager reportManager;
    private final ReportRepository reportRepository;
    private final DtoConverter dtoConverter;

    public ReportService(UserFinder userFinder, ReportFinder reportFinder, ReportValidator reportValidator,
                         ReportManager reportManager, ReportRepository reportRepository, DtoConverter dtoConverter) {
        this.userFinder = userFinder;
        this.reportFinder = reportFinder;
        this.reportValidator = reportValidator;
        this.reportManager = reportManager;
        this.reportRepository = reportRepository;
        this.dtoConverter = dtoConverter;
    }

    // [FIX] AddReportRequest가 record로 불변 → targetType/targetId를 파라미터로 분리해서 받음
    @Transactional
    public ReportResponse report(AddReportRequest request, ReportTargetType targetType, Long targetId) {
        User reporter = userFinder.findById(request.reporterId());
        reportValidator.validateDuplicateReport(reporter.getId(), targetType, targetId);

        if (targetType == ReportTargetType.POST) {
            return reportPost(reporter, targetId, request.reason());
        }
        return reportComment(reporter, targetId, request.reason());
    }

    @Transactional
    public ReportResponse resolveReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportException(ErrorType.NOT_FOUND_REPORT));
        report.resolve();
        return dtoConverter.toResponse(report);
    }

    private ReportResponse reportPost(User reporter, Long postId, ReportReason reason) {
        Post post = reportFinder.findPost(postId);
        reportValidator.validateNotOwnPost(reporter, post);
        reportValidator.validatePostNotHidden(post);

        Report report = reportManager.saveReport(reporter, ReportTargetType.POST, postId, reason);
        reportManager.applyReportToPost(post);
        return dtoConverter.toResponse(report);
    }

    private ReportResponse reportComment(User reporter, Long commentId, ReportReason reason) {
        Comment comment = reportFinder.findComment(commentId);
        reportValidator.validateNotOwnComment(reporter, comment);
        reportValidator.validateCommentNotHidden(comment);

        Report report = reportManager.saveReport(reporter, ReportTargetType.COMMENT, commentId, reason);
        reportManager.applyReportToComment(comment);
        return dtoConverter.toResponse(report);
    }
}