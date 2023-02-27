package com.sparta.spartaminiproject.domain.comment.controller;

import com.sparta.spartaminiproject.domain.comment.dto.CommentDto;
import com.sparta.spartaminiproject.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/{boardId}/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public String postComment(@PathVariable Long boardId, @RequestBody CommentDto.Request commentRequestDto) {
        commentService.writeComment(boardId, commentRequestDto);
        return "작성 성공";
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public String putComment(@PathVariable Long boardId, @PathVariable Long id, @RequestBody CommentDto.Request commentRequestDto) {
        commentService.editComment(boardId, id, commentRequestDto);
        return "수정 성공";
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long id) {
        commentService.removeComment(boardId, id);
        return "삭제 성공";
    }
}
