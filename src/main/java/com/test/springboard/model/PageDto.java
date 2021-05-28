package com.test.springboard.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageDto {
    private Integer curPage; // 현재 페이지
    private Integer curBlockNum; // 현재 블럭 페이지 번호
    private Integer blockPageNumCount; // 전체 블럭의 개수
    private Integer lastPageBlockNum; // 마지막 블럭 페이지의 번호

    @Builder
    public PageDto(Integer curPage, Integer curBlockNum, Integer blockPageNumCount, Integer lastPageBlockNum) {
        this.curPage = curPage;
        this.curBlockNum = curBlockNum;
        this.blockPageNumCount = blockPageNumCount;
        this.lastPageBlockNum = lastPageBlockNum;
    }
}
