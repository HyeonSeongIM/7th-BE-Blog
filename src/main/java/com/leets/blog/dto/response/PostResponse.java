package com.leets.blog.dto.response;

public record PostResponse(
        Long postId,
        String title,
        String content
) {
}
