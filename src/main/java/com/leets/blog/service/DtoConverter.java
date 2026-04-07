package com.leets.blog.service;

import com.leets.blog.dto.response.StringResponse;
import com.leets.blog.dto.request.AddPostRequest;
import com.leets.blog.dto.request.UpdatePostRequest;
import com.leets.blog.dto.response.PostResponse;
import com.leets.blog.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

    // NOTE : StringRequest -> StringResponse
    public StringResponse convert(String string) {
        return new StringResponse(string, string);
    }

    public Post toPost(AddPostRequest request) {
        return new Post(
                request.title(),
                request.content()
        );
    }

    public Post toPost(UpdatePostRequest request) {
        return new Post(
                request.title(),
                request.content()
        );
    }

    public PostResponse toDTO(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent()
        );
    }


}
