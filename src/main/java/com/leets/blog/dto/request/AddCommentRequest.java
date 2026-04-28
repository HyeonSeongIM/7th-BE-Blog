package com.leets.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddCommentRequest(

        @NotBlank(message = "댓글 내용은 필수입니다.")
        String content,

        @NotNull(message = "게시글 ID는 필수입니다.")
        Long postId,

        @NotNull(message = "유저 ID는 필수입니다.")
        Long userId
) {}