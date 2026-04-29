package com.leets.blog.service;

import com.leets.blog.entity.Comment;

import com.leets.blog.support.error.CommentException;
import com.leets.blog.support.error.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class CommentValidator {

    public void validateActive(Comment comment) {
        if (comment.isHidden()) {
            throw new CommentException(ErrorType.COMMENT_ALREADY_HIDDEN);
        }
    }

    public void validateOwner(Comment comment, Long requestUserId) {
        if (!comment.getUser().getId().equals(requestUserId)) {
            throw new CommentException(ErrorType.COMMENT_ACCESS_DENIED);
        }
    }

    public void validateNotDuplicate(boolean isDuplicate) {
        if (isDuplicate) {
            throw new CommentException(ErrorType.COMMENT_DUPLICATE_REQUEST);
        }
    }

    public void validateNotAccepted(boolean hasAcceptedComment) {
        if (hasAcceptedComment) {
            throw new CommentException(ErrorType.COMMENT_ALREADY_ACCEPTED);
        }
    }
}