package com.leets.blog.service;

import com.leets.blog.dto.request.AddCommentRequest;
import com.leets.blog.dto.request.UpdateCommentRequest;
import com.leets.blog.dto.response.CommentResponse;
import com.leets.blog.entity.Comment;
import com.leets.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentFinder commentFinder;
    private final CommentManager commentManager;
    private final CommentValidator commentValidator;
    private final DtoConverter dtoConverter;
    private final CommentRepository commentRepository;

    public CommentService(CommentFinder commentFinder, CommentManager commentManager, CommentValidator commentValidator, DtoConverter dtoConverter, CommentRepository commentRepository) {
        this.commentFinder = commentFinder;
        this.commentManager = commentManager;
        this.commentValidator = commentValidator;
        this.dtoConverter = dtoConverter;
        this.commentRepository = commentRepository;
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentFinder.findCommentsByPostId(postId);

        return comments.stream()
                .map(dtoConverter::toCommentDTO)
                .collect(Collectors.toList());
    }

    public CommentResponse getCommentById(Long commentId) {
        Comment comment = commentFinder.findCommentById(commentId);

        return dtoConverter.toCommentDTO(comment);
    }

    public CommentResponse addComment(AddCommentRequest request) {
        // 중복 요청 방지: 동일 유저가 동일 게시글에 동일 내용의 댓글을 이미 작성했는지 확인
        boolean isDuplicate = commentRepository.existsByContentAndUserIdAndPostId(
                request.content(), request.userId(), request.postId()
        );
        commentValidator.validateNotDuplicate(isDuplicate);

        Comment comment = dtoConverter.toComment(request);
        Comment savedComment = commentManager.add(comment, request.postId(), request.userId());

        return dtoConverter.toCommentDTO(savedComment);
    }

    public CommentResponse updateComment(Long commentId, Long requestUserId, UpdateCommentRequest request) {
        Comment comment = commentFinder.findCommentById(commentId);

        // 상태 검증: HIDDEN 상태의 댓글은 수정 불가
        commentValidator.validateActive(comment);
        // 소유자 검증: 본인 댓글만 수정 가능
        commentValidator.validateOwner(comment, requestUserId);

        Comment updatedComment = commentManager.update(commentId, request.content());

        return dtoConverter.toCommentDTO(updatedComment);
    }

    public void deleteComment(Long commentId, Long requestUserId) {
        Comment comment = commentFinder.findCommentById(commentId);

        // 상태 검증: 이미 삭제된(HIDDEN) 댓글 처리 방지
        commentValidator.validateActive(comment);
        // 소유자 검증: 본인 댓글만 삭제 가능
        commentValidator.validateOwner(comment, requestUserId);

        commentManager.delete(commentId);
    }
}