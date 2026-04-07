package com.leets.blog.controller.v1;

import com.leets.blog.dto.request.StringRequest;
import com.leets.blog.dto.response.StringResponse;
import com.leets.blog.service.DtoConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final DtoConverter dtoConverter;

    public TestController(DtoConverter dtoConverter) {
        this.dtoConverter = dtoConverter;
    }

    @PostMapping("/string/repeat")
    public StringResponse stringRepeat(@RequestBody StringRequest stringRequest) {

        StringResponse result = dtoConverter.convert(stringRequest.string());

        return result;
    }
}
