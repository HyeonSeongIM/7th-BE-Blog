package com.leets.blog.dto.response;

import com.leets.blog.entity.enums.ContentStatus;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        boolean liked,
        LocalDateTime createAt,
        ContentStatus status,
        int reportCount,
        Long userId,
        Long postId
) {}