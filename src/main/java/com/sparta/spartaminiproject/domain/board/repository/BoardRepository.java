package com.sparta.spartaminiproject.domain.board.repository;

import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.common.utill.UserDormitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByDormitory(UserDormitory dormitory, Pageable pageable);
}
