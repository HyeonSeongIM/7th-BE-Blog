package com.leets.blog.service;

import com.leets.blog.entity.Post;
import com.leets.blog.repository.PostRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostManager {

    private final PostRepository postRepository;

    public PostManager(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post add (Post post) {
        return postRepository.save(post);
    }

    // NOTE : userId 제외
    @Transactional
    public Post update (Long postId, Post post) {

        Post savedPost = postRepository.findById(postId).orElseThrow(RuntimeException::new);

        savedPost.setTitle(post.getTitle());
        savedPost.setContent(post.getContent());

        return savedPost;
    }

    // NOTE : userId 제외
    @Transactional
    public void delete (Long postId) {
        postRepository.deleteById(postId);
    }
}
