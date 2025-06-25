package com.back.domain.post.post.entity;

import com.back.domain.post.postcomment.entity.PostComment;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {
    private String title;
    private String content;

    @OneToMany(mappedBy = "post", fetch= FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostComment addComment(String content) {
        PostComment postComment = new PostComment(this, content);
        comments.add(postComment);
        return postComment;
    }

    public PostComment findCommentById(int id) {
        return  comments.stream()
                .filter(comment -> comment.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean deleteComment(PostComment postComment) {
        comments.remove(postComment);

        return false;
    }
}
