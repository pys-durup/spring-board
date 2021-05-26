package com.test.springboard.service;

import com.test.springboard.model.BoardDto;
import com.test.springboard.model.BoardEntity;
import com.test.springboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService{
    private BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 저장
    @Override
    @Transactional
    public long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    // 게시글 목록
    @Override
    public List getBoardlist() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntities) {
            BoardDto boardDto = BoardDto.builder()
                    .id(boardEntity.getId())
                    .title(boardEntity.getTitle())
                    .memo(boardEntity.getMemo())
                    .writer(boardEntity.getWriter())
                    .createDate(boardEntity.getCreateDate())
                    .hit(boardEntity.getHit())
                    .build();

            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    // 하나의 게시글 정보
    @Override
    public BoardDto getPostOne(Long id) {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityOptional.get();

        BoardDto boardDto = BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .memo(boardEntity.getMemo())
                .writer(boardEntity.getWriter())
                .createDate(boardEntity.getCreateDate())
                .hit(boardEntity.getHit())
                .build();

        return boardDto;
    }
}
