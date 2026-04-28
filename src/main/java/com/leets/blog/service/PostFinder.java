package com.leets.blog.service;

import com.leets.blog.dto.request.AddCommentRequest;
import com.leets.blog.dto.response.CommentResponse;
import com.leets.blog.entity.Comment;
import com.leets.blog.entity.Post;
import com.leets.blog.repository.PostRepository;
import com.leets.blog.support.error.ErrorType;
import com.leets.blog.support.error.PostException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostFinder {

    private final PostRepository postRepository;

    public PostFinder(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findPosts() {
        List<Post> posts = postRepository.findAll();

        if (posts.isEmpty()) {
            throw new PostException(ErrorType.NOT_EXIST_POST);
        }

        return posts;
    }

    public Post findPostByPostId(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorType.NOT_FOUND_POST));

        if (post.isHidden()) {
            throw new PostHiddenException();
        }

        return post;
    }

    public CommentResponse toCommentDTO(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.isLiked(),
                comment.getCreateAt(),
                comment.getStatus(),
                comment.getReportCount(),
                comment.getUser() != null ? comment.getUser().getId() : null,
                comment.getPost() != null ? comment.getPost().getId() : null
        );
    }

    public Comment toComment(AddCommentRequest request) {
        return new Comment(request.content());
    }
}
