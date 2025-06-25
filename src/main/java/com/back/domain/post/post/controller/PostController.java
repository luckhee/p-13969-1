package com.back.domain.post.post.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import com.back.domain.post.postcomment.entity.PostComment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

public class PostController {
    private final PostService postService;

    @GetMapping("/posts/write")
    public String showWrite(@ModelAttribute("form") WriteForm form) {
        return "post/post/write";
    }

    @ModelAttribute("siteName")
    public String siteName() {
        return "커뮤니티 게시판 A";
    }

    @AllArgsConstructor
    @Getter
    public static class WriteForm {
        @NotBlank(message = "01-title-제목을 입력해주세요")
        @Size(min = 2, max = 10 , message = "02-title-2자 이상 10자 이하로 작성해주세요")
        private String title;
        @NotBlank(message = "03-content-내용을 입력해주세요")
        @Size(min = 2, max = 100, message = "04-content-2자 이상 100자 이하로 작성해주세요.")
        private String content;
    }
    @PostMapping("/posts/write")
    @Transactional
    public String write(
            @ModelAttribute("form") @Valid WriteForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "post/post/write";
        }

        Post post = postService.write(form.title, form.content);

        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{id}")
    public String showDetail(Model model, @PathVariable int id) {
        Post post = postService.findById(id);

        model.addAttribute("post", post);
        return "post/post/detail";
    }

    @GetMapping("/posts/list")
    public String showList(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "post/post/list";
    }


    @AllArgsConstructor
    @Setter
    @Getter
    public static class ModifyForm {
        @NotBlank(message = "01-title-제목을 입력해주세요")
        @Size(min = 2, max = 10 , message = "02-title-2자 이상 10자 이하로 작성해주세요")
        private String title;
        @NotBlank(message = "03-content-내용을 입력해주세요")
        @Size(min = 2, max = 100, message = "04-content-2자 이상 100자 이하로 작성해주세요.")
        private String content;
    }

    @PostMapping("/posts/{id}/modify")
    @Transactional(readOnly = true)
    public String showModify(@PathVariable int id,
                             @ModelAttribute("form") ModifyForm form,
                             Model model) { // @ModelAttribute("form") ModifyForm modifyForm는 뷰에서 form 객체를 사용하기 위해서
        Post post = postService.findById(id);

        // postService.findById(id)로 가져온 post 객체를 뷰에서 사용하기 위해서 model에 추가
        model.addAttribute("post", post);

        form.setTitle(post.getTitle());
        form.setContent(post.getContent());
        return "post/post/modify";
    }

    @GetMapping("/posts/{id}/modify")
    @Transactional
    public String modify(@ModelAttribute("form") @Valid ModifyForm form,
                        BindingResult bindingResult,
                         Model model,
                         @PathVariable int id
    ) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);

        if(bindingResult.hasErrors()) {
            return "/post/post/write";
        }

        postService.modify(post, form.getTitle(), form.getContent());
        return "redirect:/posts/" +post.getId();
    }

    public void modifyComment(PostComment postComment, String content) {
        postComment.modify(content);
    }


}
