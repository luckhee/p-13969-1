package com.back.domain.post.postcomment.service;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.postcomment.entity.PostComment;
import com.back.domain.post.postcomment.repository.PostCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public PostComment write(Post post, String content) {
        PostComment postComment = new PostComment(post, content);

        return postComment;
    }
}
