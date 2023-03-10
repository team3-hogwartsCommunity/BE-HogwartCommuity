package com.sparta.spartaminiproject.domain.board.controller;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.security.user.UserDetailsImpl;
import com.sparta.spartaminiproject.domain.board.dto.BoardRequestDto;
import com.sparta.spartaminiproject.domain.board.dto.BoardResponseDto;
import com.sparta.spartaminiproject.domain.board.service.BoardService;
import com.sparta.spartaminiproject.common.utill.UserDormitory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    // 기숙사로 게시글 리스트 조회
    @GetMapping("/boards")
    public BoardResponseDto.BoardListWithTotalCount getBoardListFilterDormitory(@RequestParam UserDormitory dormitory, @RequestParam int page, @RequestParam int size) {
        return boardService.showBoardListFilterDormitory(dormitory, page, size);
    }

    // 게시글 하나 조회
    @GetMapping("/board")
    public BoardResponseDto.OneBoard getBoard(@RequestParam Long id) {
        return boardService.showBoard(id);
    }

    // 게시글 작성
    @PostMapping("/board")
    public ResponseEntity<SendMessageDto> postBoard(@RequestBody BoardRequestDto.Write boardWriteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.writeBoard(boardWriteRequestDto, userDetails.getUser());
    }

    // 게시글 수정
    @PutMapping("/board/{id}")
    public ResponseEntity<SendMessageDto> putBoard(@PathVariable Long id, @RequestBody BoardRequestDto.Edit boardWriteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.editBoard(id, boardWriteRequestDto, userDetails.getUser());
    }

    // 게시글 삭제
    @DeleteMapping("/board/{id}")
    public ResponseEntity<SendMessageDto> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.removeBoard(id, userDetails.getUser());

    }

    // 게시글 좋아요
    @PostMapping("/board/{id}/like")
    public ResponseEntity<SendMessageDto> postBoardLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.toggleBoardLike(id, userDetails.getUser());
    }
}
