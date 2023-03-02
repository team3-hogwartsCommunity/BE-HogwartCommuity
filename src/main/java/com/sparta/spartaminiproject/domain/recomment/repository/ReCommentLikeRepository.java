package com.sparta.spartaminiproject.domain.recomment.repository;

import com.sparta.spartaminiproject.domain.recomment.entity.ReCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReCommentLikeRepository extends JpaRepository<ReCommentLike, Long> {
    Optional<ReCommentLike> findByReCommentIdAndUserId(Long reCommentId, Long userId);
    Long countByReCommentIdAndIsShow(Long reCommentId, Boolean isShow);
}
