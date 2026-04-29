package com.leets.blog.dto.response;

import com.leets.blog.entity.enums.ContentStatus;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        boolean liked,
        // [ADD] 채택 여부 — Comment 엔티티에 accepted 필드 추가했으므로 응답에도 포함
        boolean accepted,
        LocalDateTime createAt,
        ContentStatus status
        // [REMOVE] reportCount, userId, postId — 응답 DTO에 내부 집계값/FK를 노출하는 것은 불필요.
        //           필요하다면 별도 상세 응답 DTO(CommentDetailResponse)로 분리 권장.
) {}