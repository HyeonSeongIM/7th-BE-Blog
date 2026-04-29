package com.leets.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(

        // [FIX] AddPostRequest와 기준 통일 (기존: "> 10"으로 불일치)
        @NotBlank(message = "제목은 필수입니다.")
        @Size(min = 10, message = "제목은 최소 10자 이상이어야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content
) {}