package com.leets.blog.service;

import com.leets.blog.dto.request.AddPostRequest;
import com.leets.blog.dto.request.UpdatePostRequest;
import com.leets.blog.dto.response.PostResponse;
import com.leets.blog.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostFinder postFinder;
    private final PostManager postManager;
    private final PostValidator postValidator;
    private final DtoConverter dtoConverter;

    public PostService(PostFinder postFinder, PostManager postManager,
                       PostValidator postValidator, DtoConverter dtoConverter) {
        this.postFinder = postFinder;
        this.postManager = postManager;
        this.postValidator = postValidator;
        this.dtoConverter = dtoConverter;
    }

    public List<PostResponse> getPosts() {
        return postFinder.findPosts().stream()
                .map(dtoConverter::toDTO)
                .collect(Collectors.toList());
    }

    public PostResponse getPostByPostId(Long postId) {
        Post post = postFinder.findPostByPostId(postId);
        postValidator.validateVisible(post);
        return dtoConverter.toDTO(post);
    }

    @Transactional
    public PostResponse addPost(AddPostRequest request) {
        Post post = dtoConverter.toPost(request);
        return dtoConverter.toDTO(postManager.add(post));
    }

    /**
     * [FIX] 기존 코드는 dtoConverter.toPost(request)로 새 Post 엔티티를 만들어 postManager.update()에 전달했는데,
     * PostManager.update()가 내부에서 savedPost를 다시 조회해 필드를 복사하는 이중 조회 구조였음.
     * PostFinder에서 조회 후 직접 setter를 호출하는 단순한 구조로 변경.
     */
    @Transactional
    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        Post post = postFinder.findPostByPostId(postId);
        post.setTitle(request.title());
        post.setContent(request.content());
        return dtoConverter.toDTO(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        postManager.delete(postId);
    }

    @Transactional
    public PostResponse hidePost(Long postId) {
        Post post = postFinder.findPostByPostId(postId);
        postValidator.validateNotAlreadyHidden(post);
        post.hide();
        return dtoConverter.toDTO(post);
    }

    @Transactional
    public PostResponse activatePost(Long postId) {
        Post post = postFinder.findPostByPostId(postId);
        postValidator.validateNotAlreadyActive(post);
        post.activate();
        return dtoConverter.toDTO(post);
    }
}