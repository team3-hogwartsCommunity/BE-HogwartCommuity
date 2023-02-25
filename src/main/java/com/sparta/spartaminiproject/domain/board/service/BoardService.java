package com.sparta.spartaminiproject.domain.board.service;

import com.sparta.spartaminiproject.domain.board.dto.BoardRequestDto;
import com.sparta.spartaminiproject.domain.board.dto.BoardResponseDto;
import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.domain.board.repository.BoardRepository;
import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 기숙사로 게시글 리스트 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDto.BoardList> showBoardListFilterDormitory(UserDormitory dormitory) {
        List<Board> allByDormitory = boardRepository.findAllByDormitory(dormitory);


        // 글이 없는 경우 그냥 빈 배열
        if (allByDormitory.size() == 0) {
            return new ArrayList<>();
        }

        List<BoardResponseDto.BoardList> boardDtoList = new ArrayList<>(allByDormitory.size());
        for (Board board : allByDormitory) {
            boardDtoList.add(new BoardResponseDto.BoardList(board));
        }

        return boardDtoList;
    }

    // 게시글 하나 조회
    @Transactional(readOnly = true)
    public BoardResponseDto.OneBoard showBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        return new BoardResponseDto.OneBoard(board);
    }

    // 게시글 작성
    @Transactional
    public void writeBoard(BoardRequestDto.Write boardWriteRequestDto) {
        boardRepository.save(new Board(boardWriteRequestDto));
    }

    // 게시글 수정
    @Transactional
    public void editBoard(Long id, BoardRequestDto.Edit boardEditRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        board.update(boardEditRequestDto);
    }

    // 게시글 삭제
    @Transactional
    public void removeBoard(Long id) {
        boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boardRepository.deleteById(id);
    }
}
