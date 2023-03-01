package com.sparta.spartaminiproject.domain.recomment.repository;

import com.sparta.spartaminiproject.domain.recomment.entity.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {
    List<ReComment> findAllByCommentId(Long commentId);
    Optional<ReComment> findByCommentIdAndId(Long commentId, Long id);

}
