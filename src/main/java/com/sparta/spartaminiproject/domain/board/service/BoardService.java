package com.sparta.spartaminiproject.domain.board.service;

import com.sparta.spartaminiproject.domain.board.dto.BoardRequestDto;
import com.sparta.spartaminiproject.domain.board.dto.BoardResponseDto;
import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.domain.board.entity.BoardLike;
import com.sparta.spartaminiproject.domain.board.repository.BoardLikeRepository;
import com.sparta.spartaminiproject.domain.board.repository.BoardRepository;
import com.sparta.spartaminiproject.domain.comment.dto.CommentDto;
import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.comment.repository.CommentLikeRepository;
import com.sparta.spartaminiproject.domain.comment.repository.CommentRepository;
import com.sparta.spartaminiproject.domain.user.entity.User;
import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    // 기숙사로 게시글 리스트 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto.BoardList> showBoardListFilterDormitory(UserDormitory dormitory, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        List<Board> boardList = boardRepository.findAllByDormitory(dormitory, pageable).getContent();   // getContent()로 List로 반환 받을 수 있음

        if (boardList.size() == 0) {
            throw new NullPointerException("아직 게시글이 존재하지 않습니다.");
        }

        List<BoardResponseDto.BoardList> boardDtoList = new ArrayList<>(boardList.size());
        for (Board board : boardList) {
            boardDtoList.add(new BoardResponseDto.BoardList(board, boardLikeRepository.countByBoardIdAndIsShow(board.getId(), 1)));
        }

        return boardDtoList;
    }

    // 게시글 하나 조회
    @Transactional(readOnly = true)
    public BoardResponseDto.OneBoard showBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        List<Comment> commentList = commentRepository.findAllByBoardId(id);
        if (commentList.size() == 0) {
            return new BoardResponseDto.OneBoard(board, boardLikeRepository.countByBoardIdAndIsShow(id, 1), new ArrayList<>());
        }

        List<CommentDto.Response> commentResponseDtoList = new ArrayList<>(commentList.size());

        for (Comment comment : commentList) {
            commentResponseDtoList.add(new CommentDto.Response(comment, commentLikeRepository.countByCommentIdAndIsShow(comment.getId(), 1)));
        }

        return new BoardResponseDto.OneBoard(board, boardLikeRepository.countByBoardIdAndIsShow(id, 1), commentResponseDtoList);
    }

    // 게시글 작성
    @Transactional
    public void writeBoard(BoardRequestDto.Write boardWriteRequestDto, User user) {
        if (user.getDormitory() != boardWriteRequestDto.getDormitory()) {
            throw new IllegalArgumentException("해당 게시판의 기숙사 학생이 아닙니다.");
        }

        boardRepository.save(new Board(boardWriteRequestDto, user));
    }

    // 게시글 수정
    @Transactional
    public void editBoard(Long id, BoardRequestDto.Edit boardEditRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (user.getId() != board.getUser().getId()) {
            throw new IllegalArgumentException("해당 글의 작성자가 아닙니다.");
        }

        board.update(boardEditRequestDto);
    }

    // 게시글 삭제
    @Transactional
    public void removeBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        if (user.getId() != board.getUser().getId()) {
            throw new IllegalArgumentException("해당 글의 작성자가 아닙니다.");
        }

        boardRepository.deleteById(id);
    }

    // 게시글 좋아요
    @Transactional
    public String toggleBoardLike(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Optional<BoardLike> boardLikeOptional = boardLikeRepository.findByBoardIdAndUserId(id, user.getId());
        if (boardLikeOptional.isPresent()) {
            BoardLike boardLike = boardLikeOptional.get();
            if (boardLike.getIsShow() == 1) {
                boardLike.toggleLike(0);
                return "안 좋아요";
            } else {
                boardLike.toggleLike(1);
            }
        } else {
            boardLikeRepository.save(new BoardLike(user, board));
        }

        return "좋아요";
    }
}
