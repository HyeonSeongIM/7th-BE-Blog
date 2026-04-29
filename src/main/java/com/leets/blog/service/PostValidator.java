package com.leets.blog.service;

import com.leets.blog.entity.Post;
import com.leets.blog.support.error.ErrorType;
import com.leets.blog.support.error.PostException;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    public void validateVisible(Post post) {
        if (post.isHidden()) {
            throw new PostException(ErrorType.POST_ALREADY_HIDDEN);
        }
    }

    public void validateNotAlreadyHidden(Post post) {
        if (post.isHidden()) {
            throw new PostException(ErrorType.POST_ALREADY_HIDDEN);
        }
    }

    public void validateNotAlreadyActive(Post post) {
        if (post.isActive()) {
            throw new PostException(ErrorType.POST_ALREADY_ACTIVE);
        }
    }
}