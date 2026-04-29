package com.leets.blog.service;

import com.leets.blog.entity.Comment;
import com.leets.blog.entity.Post;
import com.leets.blog.repository.CommentRepository;
import com.leets.blog.repository.PostRepository;
import com.leets.blog.support.error.ErrorType;
import com.leets.blog.support.error.ReportException;
import org.springframework.stereotype.Component;

@Component
public class ReportFinder {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public ReportFinder(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId)
                // [FIX] PostNotFoundException → 공통 ErrorType 기반 ReportException으로 통일
                .orElseThrow(() -> new ReportException(ErrorType.NOT_FOUND_POST));
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                // [FIX] CommentNotFoundException → 공통 ErrorType 기반 ReportException으로 통일
                .orElseThrow(() -> new ReportException(ErrorType.NOT_FOUND_COMMENT));
    }
}