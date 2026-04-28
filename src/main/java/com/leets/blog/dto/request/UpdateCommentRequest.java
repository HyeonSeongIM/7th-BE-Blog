package com.leets.blog.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(

        @NotBlank(message = "수정할 댓글 내용은 필수입니다.")
        String content
) {}