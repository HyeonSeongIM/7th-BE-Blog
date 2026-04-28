package com.leets.blog.service;

import com.leets.blog.dto.response.ReportResponse;
import com.leets.blog.dto.response.StringResponse;
import com.leets.blog.dto.request.AddPostRequest;
import com.leets.blog.dto.request.UpdatePostRequest;
import com.leets.blog.dto.response.PostResponse;
import com.leets.blog.entity.Post;
import com.leets.blog.entity.Report;
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

    public ReportResponse toResponse(Report report) {
        return new ReportResponse(
                report.getId(),
                report.getReporter().getId(),
                report.getTargetType(),
                report.getTargetId(),
                report.getReason(),
                report.getCreatedAt()
        );
    }

}
