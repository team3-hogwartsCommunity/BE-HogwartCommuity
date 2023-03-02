package com.sparta.spartaminiproject.domain.comment.repository;

import com.sparta.spartaminiproject.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
    Long countByCommentIdAndIsShow(Long commentId, Boolean isShow);
}
