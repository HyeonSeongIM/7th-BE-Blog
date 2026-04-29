package com.leets.blog.service;

import com.leets.blog.entity.Comment;
import com.leets.blog.repository.CommentRepository;
import com.leets.blog.support.error.CommentException;
import com.leets.blog.support.error.ErrorType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentFinder {

    private final CommentRepository commentRepository;

    public CommentFinder(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorType.NOT_FOUND_COMMENT));
    }

    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }
}