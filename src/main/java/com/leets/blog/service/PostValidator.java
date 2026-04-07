package com.leets.blog.service;

import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    // NOTE : 존재하지 않는 게시글 접근 시
    public void validateGet() {
        throw new IllegalArgumentException("작성 중");
    }


    // NOTE : 잘못된 입력값 요청 시
    public void validateNew() {
        throw new IllegalArgumentException("작성 중");
    }

}
