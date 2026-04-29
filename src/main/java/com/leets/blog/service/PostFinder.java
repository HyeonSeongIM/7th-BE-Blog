package com.leets.blog.service;

import com.leets.blog.entity.Post;
import com.leets.blog.repository.PostRepository;
import com.leets.blog.support.error.ErrorType;
import com.leets.blog.support.error.PostException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostFinder {

    private final PostRepository postRepository;

    public PostFinder(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findPosts() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            throw new PostException(ErrorType.NOT_EXIST_POST);
        }
        return posts;
    }

    public Post findPostByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorType.NOT_FOUND_POST));
        // [FIX] 숨김 게시글 접근 차단을 PostFinder가 아닌 서비스 레이어에서 결정하도록 분리.
        // Finder는 순수 조회만 담당. 상태 검증은 각 서비스/validator 책임.
        return post;
    }
}