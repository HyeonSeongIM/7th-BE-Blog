package com.leets.blog.controller.v1;

import com.leets.blog.dto.request.AddPostRequest;
import com.leets.blog.dto.request.UpdatePostRequest;
import com.leets.blog.dto.response.PostResponse;
import com.leets.blog.service.PostService;
import com.leets.blog.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ApiResponse<List<PostResponse>> getPosts() {
        return ApiResponse.success(postService.getPosts());
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long postId) {
        return ApiResponse.success(postService.getPostByPostId(postId));
    }

    @PostMapping
    public ApiResponse<PostResponse> writePost(@RequestBody AddPostRequest request) {
        return ApiResponse.success(postService.addPost(request));
    }

    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request
    ) {
        return ApiResponse.success(postService.updatePost(postId, request));
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.success();
    }

    /**
     * PATCH /api/v1/posts/{id}/hide
     * 게시물 숨김 처리 (관리자용)
     */
    @PatchMapping("/{id}/hide")
    public ApiResponse<PostResponse> hidePost(@PathVariable Long id) {
        return ApiResponse.success(postService.hidePost(id));
    }

    /**
     * PATCH /api/v1/posts/{id}/activate
     * 게시물 활성화 (관리자용)
     */
    @PatchMapping("/{id}/activate")
    public ApiResponse<PostResponse> activatePost(@PathVariable Long id) {
        return ApiResponse.success(postService.activatePost(id));
    }
}