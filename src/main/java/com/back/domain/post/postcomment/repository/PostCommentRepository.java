package com.back.domain.post.postcomment.repository;

import com.back.domain.post.postcomment.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

}
