package com.test.springboard.service;

import com.test.springboard.model.BoardDto;
import com.test.springboard.model.BoardEntity;
import com.test.springboard.model.PageDto;
import com.test.springboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BoardServiceImpl implements BoardService{
    private BoardRepository boardRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 3; //블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4; //한 페이지에 존재하는 게시글 수

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
    public List<BoardDto> getBoardlist(Pageable pageable) {
        Page<BoardEntity> page = boardRepository.findAll(pageable);

        List<BoardEntity> boardEntities = page.getContent();
        List boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntities) {
            BoardDto boardDto = entityToDto(boardEntity);
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    // 게시글 수
    @Transactional
    public Long getBoardCount() {
        return boardRepository.count();
    }

    // 페이지 정보
//    public PageDto getBlockNum(Integer curPageNum, String keyword) {
//        Long postsTotalCount;
//        if (keyword.equals("")) {
//            postsTotalCount = this.getBoardCount();
//        } else {
//            Page<BoardEntity> page = boardRepository.findAll(pageable);
//            postsTotalCount =
//        }
//
//        Integer curBlockNum = curPageNum % BLOCK_PAGE_NUM_COUNT != 0
//                ? curPageNum / BLOCK_PAGE_NUM_COUNT
//                : curPageNum / BLOCK_PAGE_NUM_COUNT - 1; // 현 블럭 페이지의 위치
//
//
//        Integer lastPageBlockNum = ((int)(Math.ceil((postsTotalCount / PAGE_POST_COUNT))) / BLOCK_PAGE_NUM_COUNT);
//
//        PageDto pageDto = new PageDto(curPageNum, curBlockNum, BLOCK_PAGE_NUM_COUNT, lastPageBlockNum);
//        return pageDto;
//    }

    // 페이징 블럭갯수 계산
    public PageDto getPageList(Integer curPageNum, String keyword, Pageable pageable) {
        Long postsTotalCount; // 총 게시글 개수

        // 총 게시글 개수 계산
        if (keyword.equals("")) { // 검색어가 없으면
            postsTotalCount = this.getBoardCount();
        } else { // 검색어가 있으면
            List<BoardEntity> boardEntityList = boardRepository.findByTitleContaining(keyword);
            postsTotalCount = Long.valueOf(boardEntityList.size());
        }
        System.out.println("postsTotalCount = " + postsTotalCount);

        // 페이지 블럭 번호를 담을 배열
        Integer[] pageList;
        // 현재 블럭이 몇번째 블럭순서에 위치하고 있는지
        Integer curBlockNum = curPageNum % BLOCK_PAGE_NUM_COUNT != 0
                ? curPageNum / BLOCK_PAGE_NUM_COUNT
                : curPageNum / BLOCK_PAGE_NUM_COUNT - 1; // 현 블럭 페이지의 위치

        Integer blockStartPageNum = 1 + (curBlockNum * BLOCK_PAGE_NUM_COUNT); // 표시할 시작
        Integer blockLastPageNum = (curBlockNum * BLOCK_PAGE_NUM_COUNT) + BLOCK_PAGE_NUM_COUNT;; // 표시할 끝

        System.out.println("curBlockNum = " + curBlockNum);
        System.out.println("blockStartPageNum = " + blockStartPageNum);



        // 총 게시물 기준으로 계산한 마지막 페이지 번호 = 총 페이지 블럭 개수 (게시글 개수 / 한 페이지에 표시할 게시글 개수)
        Integer totalBlockNum = (int) (Math.ceil(((float)postsTotalCount / PAGE_POST_COUNT)));

        // 마지막 페이지 번호
        if (blockLastPageNum > totalBlockNum) {
            blockLastPageNum = totalBlockNum;
        }

        System.out.println("blockLastPageNum = " + blockLastPageNum);

        Integer lastPageBlockNum = ((int)(Math.ceil((postsTotalCount / PAGE_POST_COUNT))) / BLOCK_PAGE_NUM_COUNT);

        pageList = new Integer[blockLastPageNum - blockStartPageNum + 1];

        // 페이지 번호 할당
        for (int val = blockStartPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        PageDto pageDto = new PageDto(curPageNum, curBlockNum, BLOCK_PAGE_NUM_COUNT, lastPageBlockNum, pageList);


        return pageDto;
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
    public List<BoardDto> searchPosts(String keyword, Pageable pageable) {
        List<BoardEntity> boardEntities = boardRepository.findByTitleContaining(keyword, pageable);
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
