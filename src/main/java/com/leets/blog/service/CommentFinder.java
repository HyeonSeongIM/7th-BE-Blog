package com.leets.blog.service;

import com.leets.blog.entity.Comment;
import com.leets.blog.exception.comment.CommentErrorCode;
import com.leets.blog.exception.comment.CommentException;
import com.leets.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CommentFinder {

    private final CommentRepository commentRepository;

    public CommentFinder(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }

    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }
}