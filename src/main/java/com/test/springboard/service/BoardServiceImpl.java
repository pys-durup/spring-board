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
            BoardDto boardDto = entityToDto(boardEntity);
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    // Entity to Dto
    private BoardDto entityToDto(BoardEntity boardEntity) {
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .memo(boardEntity.getMemo())
                .writer(boardEntity.getWriter())
                .createDate(boardEntity.getCreateDate())
                .hit(boardEntity.getHit())
                .build();
    }

    // 게시글 목록 + 검색어
    @Override
    public List<BoardDto> searchPosts(String keyword) {
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDtoList;

        for (BoardEntity boardEntity : boardEntities) {
            BoardDto boardDto = entityToDto(boardEntity);

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    // 하나의 게시글 정보
    @Override
    public BoardDto getPostOne(Long id) {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityOptional.get();

        BoardDto boardDto = entityToDto(boardEntity);

        return boardDto;
    }

    // 게시글 삭제
    @Override
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}
