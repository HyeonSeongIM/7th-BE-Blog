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
        List<PostResponse> result = postService.getPosts();
        return ApiResponse.success(result);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPostByPostId(@PathVariable Long postId) {
        PostResponse result = postService.getPostByPostId(postId);
        return ApiResponse.success(result);
    }

    @PostMapping
    public ApiResponse<PostResponse> writePost(@RequestBody AddPostRequest request) {
        PostResponse result = postService.addPost(request);
        return ApiResponse.success(result);
    }

    @PutMapping("/{postId}")
    public ApiResponse<PostResponse> reWritePost(@PathVariable Long postId, @RequestBody UpdatePostRequest request) {
        PostResponse result = postService.updatePost(postId, request);
        return ApiResponse.success(result);
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.success();
    }

}
