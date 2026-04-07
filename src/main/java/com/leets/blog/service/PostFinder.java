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
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorType.NOT_FOUND_POST));
    }
}
