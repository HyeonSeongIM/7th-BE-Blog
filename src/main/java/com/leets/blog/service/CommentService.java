package com.leets.blog.service;

import com.leets.blog.dto.request.AddCommentRequest;
import com.leets.blog.dto.request.UpdateCommentRequest;
import com.leets.blog.dto.response.CommentResponse;
import com.leets.blog.entity.Comment;
import com.leets.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentFinder commentFinder;
    private final CommentManager commentManager;
    private final CommentValidator commentValidator;
    private final DtoConverter dtoConverter;
    private final CommentRepository commentRepository;

    public CommentService(CommentFinder commentFinder, CommentManager commentManager,
                          CommentValidator commentValidator, DtoConverter dtoConverter,
                          CommentRepository commentRepository) {
        this.commentFinder = commentFinder;
        this.commentManager = commentManager;
        this.commentValidator = commentValidator;
        this.dtoConverter = dtoConverter;
        this.commentRepository = commentRepository;
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        return commentFinder.findCommentsByPostId(postId).stream()
                .map(dtoConverter::toCommentDTO)
                .collect(Collectors.toList());
    }

    public CommentResponse getCommentById(Long commentId) {
        return dtoConverter.toCommentDTO(commentFinder.findCommentById(commentId));
    }

    @Transactional
    public CommentResponse addComment(AddCommentRequest request) {
        boolean isDuplicate = commentRepository.existsByContentAndUserIdAndPostId(
                request.content(), request.userId(), request.postId()
        );
        commentValidator.validateNotDuplicate(isDuplicate);

        Comment comment = dtoConverter.toComment(request);
        Comment saved = commentManager.add(comment, request.postId(), request.userId());
        return dtoConverter.toCommentDTO(saved);
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, Long requestUserId, UpdateCommentRequest request) {
        Comment comment = commentFinder.findCommentById(commentId);
        commentValidator.validateActive(comment);
        commentValidator.validateOwner(comment, requestUserId);

        Comment updated = commentManager.update(commentId, request.content());
        return dtoConverter.toCommentDTO(updated);
    }

    @Transactional
    public void deleteComment(Long commentId, Long requestUserId) {
        Comment comment = commentFinder.findCommentById(commentId);
        commentValidator.validateActive(comment);
        commentValidator.validateOwner(comment, requestUserId);
        commentManager.delete(commentId);
    }

    @Transactional
    public CommentResponse acceptComment(Long commentId) {
        Comment comment = commentFinder.findCommentById(commentId);
        commentValidator.validateActive(comment);
        commentValidator.validateNotAccepted(comment.getPost().hasAcceptedComment());
        comment.accept();
        return dtoConverter.toCommentDTO(comment);
    }
}