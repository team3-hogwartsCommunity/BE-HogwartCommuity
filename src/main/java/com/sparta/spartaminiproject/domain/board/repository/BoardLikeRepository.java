package com.sparta.spartaminiproject.domain.board.repository;

import com.sparta.spartaminiproject.domain.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByBoardIdAndUserId(Long boardId, Long userId);
    Long countByBoardIdAndIsShow(Long boardId, Boolean isShow);
}
