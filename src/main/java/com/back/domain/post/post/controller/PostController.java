package com.back.domain.post.post.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/write")
    @ResponseBody
    public String showWrite() {
        return """
                <form method="POST" action="doWrite">
                  <input type="text" name="title" placeholder="제목" value="">
                  <br>
                  <textarea name="content" placeholder="내용"></textarea>
                  <br>
                  <input type="submit" value="작성">
                </form>
                """;
    }

    @PostMapping("/posts/doWrite")
    @ResponseBody
    @Transactional
    public String write(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String content
    ) {
        if( title.isBlank() ) {
            return blankWrite("제목");
        } else if( content.isBlank() ) {
            return blankWrite("내용");
        } else if( title.length() < 2 ) {
            return "제목은 2글자 이상 입력해 주세요.";
        } else if( content.length() < 5 ) {
            return "내용은 5글자 이상 입력해 주세요.";
        }


        Post post = postService.write(title, content);

        return "%d번 글이 생성되었습니다.".formatted(post.getId());
    }

    @GetMapping("/posts/blankwrite")
    @ResponseBody
    public String blankWrite(String blank) {
        return """
                <h1>%s을 입력해 주세요</h1>
                <form method="POST" action="doWrite">
                  <input type="text" name="title" placeholder="제목" value="">
                  <br>
                  <textarea name="content" placeholder="내용"></textarea>
                  <br>
                  <input type="submit" value="작성">
                </form>
                """.formatted(blank);
    }
}
