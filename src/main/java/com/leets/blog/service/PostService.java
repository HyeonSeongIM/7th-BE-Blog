package com.leets.blog.service;

import com.leets.blog.dto.request.AddPostRequest;
import com.leets.blog.dto.request.UpdatePostRequest;
import com.leets.blog.dto.response.PostResponse;
import com.leets.blog.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostFinder postFinder;
    private final PostManager postManager;
    private final PostValidator postValidator;
    private final DtoConverter dtoConverter;

    public PostService(PostFinder postFinder, PostManager postManager, PostValidator postValidator, DtoConverter dtoConverter) {
        this.postFinder = postFinder;
        this.postManager = postManager;
        this.postValidator = postValidator;
        this.dtoConverter = dtoConverter;
    }


    public List<PostResponse> getPosts() {
        List<Post> posts = postFinder.findPosts();

        return posts.stream()
                .map(dtoConverter::toDTO)
                .collect(Collectors.toList());
    }

    public PostResponse getPostByPostId(Long postId) {
        Post post = postFinder.findPostByPostId(postId);

        PostResponse postResponse = dtoConverter.toDTO(post);

        return postResponse;
    }

    public PostResponse addPost(AddPostRequest addPostRequest) {
        Post post = dtoConverter.toPost(addPostRequest);

        Post newPost = postManager.add(post);

        PostResponse postResponse = dtoConverter.toDTO(newPost);

        return postResponse;
    }

    // TODO : 사용자 정보 받기 + 사용자 정보 밸리데이터 넣기
    public PostResponse updatePost(Long postId, UpdatePostRequest updatePostRequest) {
        Post post = dtoConverter.toPost(updatePostRequest);

        Post newPost = postManager.update(postId, post);

        PostResponse postResponse = dtoConverter.toDTO(newPost);

        return postResponse;
    }

    // TODO : 사용자 정보 받기 + 사용자 정보 밸리데이터 넣기
    public void deletePost(Long postId) {
        postManager.delete(postId);
    }
}
