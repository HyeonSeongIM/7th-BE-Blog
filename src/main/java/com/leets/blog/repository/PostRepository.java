package com.leets.blog.repository;

import com.leets.blog.entity.Post;
import com.leets.blog.entity.enums.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByStatus(ContentStatus status);

    Optional<Post> findByIdAndStatus(Long id, ContentStatus status);
}
