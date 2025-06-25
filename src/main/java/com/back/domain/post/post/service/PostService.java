package com.back.domain.post.post.service;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.repository.PostRepository;
import com.back.domain.post.postcomment.entity.PostComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public long count() {
        return postRepository.count();
    }

    @Transactional
    public Post write(String title, String content) {
        Post post = new Post(title, content);

        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post findById(int id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void modify(Post post, @NotBlank(message = "01-title-제목을 입력해주세요")
                       @Size(min = 2, max = 10, message = "02-title-2자 이상 10자 이하로 작성해주세요")
                       String title,
                       @NotBlank(message = "03-content-내용을 입력해주세요")
                       @Size(min = 2, max = 100, message = "04-content-2자 이상 100자 이하로 작성해주세요.")
                       String content) {

    }

    public void writeComment(Post post, String content) {
        post.addComment(content);
    }

    public boolean deleteComment(Post post, PostComment postComment) {


        return post.deleteComment(postComment);
    }

    public void modifyComment(PostComment postComment, String content) {
        postComment.modify(content);
    }
}
