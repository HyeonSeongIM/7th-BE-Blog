package com.leets.blog.entity.enums;

public enum ContentStatus {
    ACTIVE,     // 정상 노출
    HIDDEN,     // 신고 누적으로 숨김 처리 (기준: 5회 이상)
    DELETED     // 삭제됨
}