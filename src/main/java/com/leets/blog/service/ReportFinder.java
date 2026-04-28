package com.leets.blog.service;

import com.leets.blog.entity.Comment;
import com.leets.blog.entity.Post;
import com.leets.blog.repository.CommentRepository;
import com.leets.blog.repository.PostRepository;
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
                .orElseThrow(PostNotFoundException::new);
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}
