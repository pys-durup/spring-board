package com.test.springboard.model;

import lombok.*;

@Getter @Setter @NoArgsConstructor @ToString
public class PageDto {
    private Integer curPage; // 현재 페이지
    private Integer curBlockNum; // 현재 블럭 페이지 번호
    private Integer blockPageNumCount; // 전체 블럭의 개수
    private Integer lastPageBlockNum; // 마지막 블럭 페이지의 번호
    private Integer[] pageList; // 페이지 번호 목록

    @Builder
    public PageDto(Integer curPage, Integer curBlockNum, Integer blockPageNumCount, Integer lastPageBlockNum, Integer[] pageList) {
        this.curPage = curPage;
        this.curBlockNum = curBlockNum;
        this.blockPageNumCount = blockPageNumCount;
        this.lastPageBlockNum = lastPageBlockNum;
        this.pageList = pageList;
    }
}
