package com.leets.blog.service;

import com.leets.blog.dto.request.AddReportRequest;
import com.leets.blog.dto.response.ReportResponse;
import com.leets.blog.entity.Comment;
import com.leets.blog.entity.Post;
import com.leets.blog.entity.Report;
import com.leets.blog.entity.User;
import com.leets.blog.entity.enums.ReportTargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private final UserFinder userFinder;
    private final ReportFinder reportFinder;
    private final ReportValidator reportValidator;
    private final ReportManager reportManager;
    private final DtoConverter reportDtoConverter;

    public ReportService(UserFinder userFinder, ReportFinder reportFinder, ReportValidator reportValidator, ReportManager reportManager, DtoConverter reportDtoConverter) {
        this.userFinder = userFinder;
        this.reportFinder = reportFinder;
        this.reportValidator = reportValidator;
        this.reportManager = reportManager;
        this.reportDtoConverter = reportDtoConverter;
    }

    @Transactional
    public ReportResponse report(AddReportRequest request) {
        User reporter = userFinder.findById(request.getReporterId());

        reportValidator.validateDuplicateReport(
                reporter.getId(),
                request.getTargetType(),
                request.getTargetId()
        );

        if (request.getTargetType() == ReportTargetType.POST) {
            return reportPost(reporter, request);
        }
        return reportComment(reporter, request);
    }

    private ReportResponse reportPost(User reporter, AddReportRequest request) {
        Post post = reportFinder.findPost(request.getTargetId());

        reportValidator.validateNotOwnPost(reporter, post);
        reportValidator.validatePostNotHidden(post);

        Report report = reportManager.saveReport(
                reporter,
                request.getTargetType(),
                request.getTargetId(),
                request.getReason()
        );

        reportManager.applyReportToPost(post);

        return reportDtoConverter.toResponse(report);
    }

    private ReportResponse reportComment(User reporter, AddReportRequest request) {
        Comment comment = reportFinder.findComment(request.getTargetId());

        reportValidator.validateNotOwnComment(reporter, comment);
        reportValidator.validateCommentNotHidden(comment);

        Report report = reportManager.saveReport(
                reporter,
                request.getTargetType(),
                request.getTargetId(),
                request.getReason()
        );

        reportManager.applyReportToComment(comment);

        return reportDtoConverter.toResponse(report);
    }
}