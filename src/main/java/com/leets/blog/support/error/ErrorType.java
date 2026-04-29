package com.leets.blog.support.error;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public enum ErrorType {

    // ── 공통 ───────────────────────────────────────────────────────────────────
    INVALID_EXCEPTION(HttpStatus.BAD_REQUEST, ErrorCode.E400, "잘못된 요청입니다.", LogLevel.ERROR),

    // ── Post ───────────────────────────────────────────────────────────────────
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, ErrorCode.E404, "게시글을 찾을 수 없습니다.", LogLevel.WARN),
    NOT_EXIST_POST(HttpStatus.NOT_FOUND, ErrorCode.E404, "게시글이 존재하지 않습니다.", LogLevel.WARN),
    POST_ALREADY_HIDDEN(HttpStatus.BAD_REQUEST, ErrorCode.E400, "이미 숨김 처리된 게시글입니다.", LogLevel.WARN),
    POST_ALREADY_ACTIVE(HttpStatus.BAD_REQUEST, ErrorCode.E400, "이미 활성화된 게시글입니다.", LogLevel.WARN),

    // ── Comment ────────────────────────────────────────────────────────────────
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, ErrorCode.E404, "댓글을 찾을 수 없습니다.", LogLevel.WARN),
    COMMENT_ALREADY_HIDDEN(HttpStatus.BAD_REQUEST, ErrorCode.E400, "이미 숨김 처리된 댓글입니다.", LogLevel.WARN),
    COMMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, ErrorCode.E403, "본인의 댓글만 수정/삭제할 수 있습니다.", LogLevel.WARN),
    COMMENT_DUPLICATE_REQUEST(HttpStatus.BAD_REQUEST, ErrorCode.E400, "동일한 내용의 댓글이 이미 존재합니다.", LogLevel.WARN),
    COMMENT_POST_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404, "댓글을 작성할 게시글을 찾을 수 없습니다.", LogLevel.WARN),
    COMMENT_USER_NOT_FOUND(HttpStatus.NOT_FOUND, ErrorCode.E404, "댓글을 작성할 사용자를 찾을 수 없습니다.", LogLevel.WARN),
    COMMENT_ALREADY_ACCEPTED(HttpStatus.BAD_REQUEST, ErrorCode.E400, "이미 채택된 댓글이 있는 게시글입니다.", LogLevel.WARN),

    // ── User ───────────────────────────────────────────────────────────────────
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, ErrorCode.E404, "사용자를 찾을 수 없습니다.", LogLevel.WARN),

    // ── Report ─────────────────────────────────────────────────────────────────
    REPORT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, ErrorCode.E400, "이미 신고한 대상입니다.", LogLevel.WARN),
    REPORT_OWN_CONTENT(HttpStatus.BAD_REQUEST, ErrorCode.E400, "본인의 게시물/댓글은 신고할 수 없습니다.", LogLevel.WARN),
    REPORT_TARGET_HIDDEN(HttpStatus.BAD_REQUEST, ErrorCode.E400, "이미 숨김 처리된 대상은 신고할 수 없습니다.", LogLevel.WARN),
    NOT_FOUND_REPORT(HttpStatus.NOT_FOUND, ErrorCode.E404, "신고 내역을 찾을 수 없습니다.", LogLevel.WARN),
    REPORT_ALREADY_RESOLVED(HttpStatus.BAD_REQUEST, ErrorCode.E400, "이미 처리 완료된 신고입니다.", LogLevel.WARN),
    ;

    private final HttpStatus status;
    private final ErrorCode code;
    private final String message;
    private final LogLevel logLevel;

    ErrorType(HttpStatus status, ErrorCode code, String message, LogLevel logLevel) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }

    public HttpStatus getStatus() { return status; }
    public ErrorCode getCode() { return code; }
    public String getMessage() { return message; }
    public LogLevel getLogLevel() { return logLevel; }
}