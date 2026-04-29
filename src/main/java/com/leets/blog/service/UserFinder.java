package com.leets.blog.service;

import com.leets.blog.entity.User;
import com.leets.blog.repository.UserRepository;
import com.leets.blog.support.error.ErrorType;
import com.leets.blog.support.error.ReportException;
import org.springframework.stereotype.Component;

@Component
public class UserFinder {

    private final UserRepository userRepository;

    public UserFinder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                // [FIX] UserNotFoundException → 공통 ErrorType으로 통일.
                // UserNotFoundException은 별도 패키지로 유지하거나 아래처럼 ReportException 대신
                // 전용 UserException으로 분리해도 됩니다.
                .orElseThrow(() -> new ReportException(ErrorType.NOT_FOUND_USER));
    }
}