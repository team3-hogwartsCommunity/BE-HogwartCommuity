package com.sparta.spartaminiproject.domain.board.repository;

import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByDormitory(UserDormitory dormitory);
}
