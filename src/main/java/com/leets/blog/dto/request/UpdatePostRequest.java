package com.leets.blog.dto.request;

import com.leets.blog.support.error.ErrorType;
import com.leets.blog.support.error.PostException;

public record UpdatePostRequest(
        String title,
        String content
) {
    public UpdatePostRequest {
        // 제목이 10자를 초과할 경우 예외 발생
        if (title != null && title.length() > 10) {
            throw new PostException(ErrorType.INVALID_EXCEPTION);
        }

        // 내용이 비어있는지 확인하는 추가 검증
        if (content == null || content.isBlank()) {
            throw new PostException(ErrorType.INVALID_EXCEPTION);
        }
    }
}
