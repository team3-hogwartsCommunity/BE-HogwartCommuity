package com.sparta.spartaminiproject.domain.board.controller;

import com.sparta.spartaminiproject.domain.board.dto.BoardRequestDto;
import com.sparta.spartaminiproject.domain.board.dto.BoardResponseDto;
import com.sparta.spartaminiproject.domain.board.service.BoardService;
import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 기숙사로 게시글 리스트 조회
    @GetMapping("/boards")
    public List<BoardResponseDto.BoardList> getBoardListFilterDormitory(@RequestParam UserDormitory dormitory) {
        return boardService.showBoardListFilterDormitory(dormitory);
    }

    // 게시글 하나 조회
    @GetMapping("/board")
    public BoardResponseDto.OneBoard getBoard(@RequestParam Long id) {
        return boardService.showBoard(id);
    }

    // 게시글 작성
    @PostMapping("/board")
    public String postBoard(@RequestBody BoardRequestDto.Write boardWriteRequestDto) {
        boardService.writeBoard(boardWriteRequestDto);
        return "작성 성공";
    }

    // 게시글 수정
    @PutMapping("/board/{id}")
    public String putBoard(@PathVariable Long id, @RequestBody BoardRequestDto.Edit boardWriteRequestDto) {
        boardService.editBoard(id, boardWriteRequestDto);
        return "수정 성공";
    }

    // 게시글 삭제
    @DeleteMapping("/board/{id}")
    public String deleteBoard(@PathVariable Long id) {
        boardService.removeBoard(id);
        return "삭제 성공";
    }
}
