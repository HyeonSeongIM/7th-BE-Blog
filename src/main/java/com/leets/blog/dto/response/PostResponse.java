package com.leets.blog.dto.response;

import com.leets.blog.entity.enums.ContentStatus;

public record PostResponse(
        Long postId,
        String title,
        String content,
        // [ADD] 게시글 상태(ACTIVE/HIDDEN)를 응답에 포함 — hide/activate API 결과 확인에 필요
        ContentStatus status
) {}