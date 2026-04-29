package com.leets.blog.controller.v1;

import com.leets.blog.dto.request.AddCommentRequest;
import com.leets.blog.dto.request.UpdateCommentRequest;
import com.leets.blog.dto.response.CommentResponse;
import com.leets.blog.service.CommentService;
import com.leets.blog.support.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * GET /api/v1/posts/{postId}/comments
     * 특정 게시글의 댓글 목록 조회
     */
    @GetMapping("/api/v1/posts/{postId}/comments")
    public ApiResponse<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        return ApiResponse.success(commentService.getCommentsByPostId(postId));
    }

    /**
     * POST /api/v1/posts/{postId}/comments
     * 댓글 작성
     */
    @PostMapping("/api/v1/posts/{postId}/comments")
    public ApiResponse<CommentResponse> addComment(
            @PathVariable Long postId,
            @RequestBody @Valid AddCommentRequest request
    ) {
        // postId는 경로에서, request body의 postId와 일치 여부는 서비스/validator에서 처리 가능
        return ApiResponse.success(commentService.addComment(request));
    }

    /**
     * PATCH /api/v1/comments/{commentId}
     * 댓글 수정
     */
    @PatchMapping("/api/v1/comments/{commentId}")
    public ApiResponse<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestBody @Valid UpdateCommentRequest request
    ) {
        return ApiResponse.success(commentService.updateComment(commentId, userId, request));
    }

    /**
     * DELETE /api/v1/comments/{commentId}
     * 댓글 삭제
     */
    @DeleteMapping("/api/v1/comments/{commentId}")
    public ApiResponse<?> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId
    ) {
        commentService.deleteComment(commentId, userId);
        return ApiResponse.success();
    }

    /**
     * PATCH /api/v1/comments/{commentId}/accept
     * 댓글 채택 — 게시글당 1개 제한
     */
    @PatchMapping("/api/v1/comments/{commentId}/accept")
    public ApiResponse<CommentResponse> acceptComment(@PathVariable Long commentId) {
        return ApiResponse.success(commentService.acceptComment(commentId));
    }
}