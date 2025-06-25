package com.back.domain.post.postcomment.entity;

import com.back.domain.post.post.entity.Post;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class PostComment extends BaseEntity {

    private String content;

    @ManyToOne
    private Post post;
    public PostComment(Post post, String content) {
        this.post= post;
        this.content = content;
    }

    public void modify(String content) {
        this.content = content;
    }
}
