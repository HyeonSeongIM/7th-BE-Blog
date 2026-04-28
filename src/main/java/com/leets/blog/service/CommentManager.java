package com.leets.blog.service;

import com.leets.blog.entity.Comment;
import com.leets.blog.entity.Post;
import com.leets.blog.entity.User;
import com.leets.blog.exception.comment.CommentErrorCode;
import com.leets.blog.exception.comment.CommentException;
import com.leets.blog.repository.CommentRepository;
import com.leets.blog.repository.PostRepository;
import com.leets.blog.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentManager {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentManager(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment add(Comment comment, Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_USER_NOT_FOUND));

        comment.assignPost(post);
        comment.assignUser(user);

        return commentRepository.save(comment);
    }

    public Comment update(Long commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        comment.updateContent(newContent);

        return comment;
    }

    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        comment.softDelete();
    }
}