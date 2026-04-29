package com.leets.blog.repository;

import com.leets.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostId(Long postId);

    // [ADD] CommentService.addComment()에서 중복 댓글 방지에 사용
    boolean existsByContentAndUserIdAndPostId(String content, Long userId, Long postId);
}
