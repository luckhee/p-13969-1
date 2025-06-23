package com.back.domain.post.post.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor

public class PostController {
    private final PostService postService;

    @GetMapping("/posts/write")

    public String showWrite() {
        return "post/post/write";
    }

    @AllArgsConstructor
    @Getter
    public static class writeForm {
        @NotBlank(message = "1-제목을 입력해주세요")
        @Size(min = 2, max = 10 , message = "2-2자 이상 10자 이하로 작성해주세요")
        private String title;
        @NotBlank(message = "3-내용을 입력해주세요")
        @Size(min = 2, max = 100, message = "4-2자 이상 100자 이하로 작성해주세요.")
        private String content;
    }

    @PostMapping("/posts/doWrite")
    @Transactional
    public String write(@Valid writeForm writeform,
                        BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return "/post/post/write";
        }

        Post post = postService.write(writeform.title, writeform.content);
        return "%d번 글이 생성되었습니다.".formatted(post.getId());
    }

}
