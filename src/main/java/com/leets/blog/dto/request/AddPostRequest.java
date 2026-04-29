package com.leets.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddPostRequest(

        // [FIX] record compact constructor에서 PostException을 직접 던지던 방식 제거.
        // DTO가 예외 클래스에 의존하는 것은 관심사 분리 위반.
        // Bean Validation(@Valid)으로 교체하고, 비즈니스 검증은 서비스 레이어에서 담당.
        // [FIX] 기존 주석 "10자 초과 시 예외"인데 실제 코드는 "< 10"(미만) → min = 10으로 의도에 맞게 수정
        @NotBlank(message = "제목은 필수입니다.")
        @Size(min = 10, message = "제목은 최소 10자 이상이어야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content
) {}