package com.sparta.spartaminiproject.domain.comment.repository;

import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long boardId);

    Optional<Comment> findByBoardIdAndId(Long boardId, Long id);
}
