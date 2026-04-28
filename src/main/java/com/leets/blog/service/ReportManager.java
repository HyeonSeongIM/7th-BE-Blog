package com.leets.blog.service;

import com.leets.blog.entity.Post;
import com.leets.blog.entity.Report;
import com.leets.blog.entity.Comment;
import com.leets.blog.entity.User;
import com.leets.blog.entity.enums.ReportReason;
import com.leets.blog.entity.enums.ReportTargetType;
import com.leets.blog.repository.CommentRepository;
import com.leets.blog.repository.PostRepository;
import com.leets.blog.repository.ReportRepository;
import org.springframework.stereotype.Component;

@Component
public class ReportManager {

    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public ReportManager(ReportRepository reportRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.reportRepository = reportRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Report saveReport(User reporter, ReportTargetType targetType, Long targetId, ReportReason reason) {
        Report report = new Report(reporter, targetType, targetId, reason);
        return reportRepository.save(report);
    }

    public void applyReportToPost(Post post) {
        post.increaseReportCount();
        postRepository.save(post);
    }

    public void applyReportToComment(Comment comment) {
        comment.increaseReportCount();
        commentRepository.save(comment);
    }
}
