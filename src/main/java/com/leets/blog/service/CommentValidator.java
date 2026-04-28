package com.leets.blog.service;

import com.leets.blog.entity.Comment;
import com.leets.blog.exception.comment.CommentErrorCode;
import com.leets.blog.exception.comment.CommentException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CommentValidator {

    /**
     * 댓글이 ACTIVE 상태인지 검증
     * 숨김 처리된 댓글에 대한 수정/삭제 등 작업 방지
     */
    public void validateActive(Comment comment) {
        if (comment.isHidden()) {
            throw new CommentException(CommentErrorCode.COMMENT_ALREADY_HIDDEN);
        }
    }

    /**
     * 댓글 작성자 본인 여부 검증
     * 다른 사용자의 댓글 수정/삭제 방지
     */
    public void validateOwner(Comment comment, Long requestUserId) {
        if (!comment.getUser().getId().equals(requestUserId)) {
            throw new CommentException(CommentErrorCode.COMMENT_ACCESS_DENIED);
        }
    }

    /**
     * 중복 요청 방지 검증
     * 동일한 내용으로 이미 존재하는 댓글 처리
     */
    public void validateNotDuplicate(boolean isDuplicate) {
        if (isDuplicate) {
            throw new CommentException(CommentErrorCode.COMMENT_DUPLICATE_REQUEST);
        }
    }
}