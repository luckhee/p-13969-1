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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

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
    @ResponseBody
    @Transactional
    public String write(@Valid writeForm writeform,
                        BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {

            String errorFieldName = "title";
            String errorMessage = bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getField() + "-" + fieldError.getDefaultMessage()).collect(Collectors.joining("<br>"));

            return getWriteFormHtml(errorFieldName,writeform.getTitle(),writeform.getContent(),errorMessage);
        }

        Post post = postService.write(writeform.title, writeform.content);
        return "%d번 글이 생성되었습니다.".formatted(post.getId());
    }


    public String getWriteFormHtml() {
        return getWriteFormHtml("","","","");
    }


    public String getWriteFormHtml(String errorFieldName, String title, String content,String errorMessage) {
        return """
                <h1>%s</h1>
                <form method="POST" action="doWrite">
                    <input type="text" name="title" placeholder="제목" value="%s" autofocus>
                    <br>
                    <textarea name="content" placeholder="내용">%s</textarea>
                    <br>
                    <input type="submit" value="작성">
                </form>
                
                <script>
                    
                    const errorFieldName = '%s';
                    
                    if(errorFieldName.length > 0 ) {
                        // 모든 폼을 다 긁어와
                        const forms = document.querySelectorAll("form");
                        // 마지막 폼
                        const lastForm = forms[forms.length - 1];
                        //focus
                        lastForm[errorFieldName].focus();               
                    }
                </script>
                
                """.formatted(errorMessage ,title, content, errorFieldName);
    }
}
