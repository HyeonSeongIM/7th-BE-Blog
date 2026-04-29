package com.leets.blog.service;

import com.leets.blog.entity.Comment;
import com.leets.blog.entity.Post;
import com.leets.blog.entity.User;
import com.leets.blog.entity.enums.ReportTargetType;
import com.leets.blog.repository.ReportRepository;
import com.leets.blog.support.error.ErrorType;
import com.leets.blog.support.error.ReportException;
import org.springframework.stereotype.Component;

@Component
public class ReportValidator {

    private final ReportRepository reportRepository;

    public ReportValidator(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void validateDuplicateReport(Long reporterId, ReportTargetType targetType, Long targetId) {
        if (reportRepository.existsByReporter_IdAndTargetTypeAndTargetId(reporterId, targetType, targetId)) {
            throw new ReportException(ErrorType.REPORT_ALREADY_EXISTS);
        }
    }

    public void validateNotOwnPost(User reporter, Post post) {
        if (post.getUser() != null && post.getUser().getId().equals(reporter.getId())) {
            throw new ReportException(ErrorType.REPORT_OWN_CONTENT);
        }
    }

    public void validateNotOwnComment(User reporter, Comment comment) {
        if (comment.getUser() != null && comment.getUser().getId().equals(reporter.getId())) {
            throw new ReportException(ErrorType.REPORT_OWN_CONTENT);
        }
    }

    public void validatePostNotHidden(Post post) {
        if (post.isHidden()) {
            throw new ReportException(ErrorType.REPORT_TARGET_HIDDEN);
        }
    }

    public void validateCommentNotHidden(Comment comment) {
        if (comment.isHidden()) {
            throw new ReportException(ErrorType.REPORT_TARGET_HIDDEN);
        }
    }
}