package com.test.springboard.model;

import lombok.*;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String writer;
    private String title;
    private String memo;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private Integer hit;
    private String deleteFlag;

    @Builder
    public BoardDto(Long id, String writer, String title, String memo, LocalDateTime createDate, LocalDateTime modifiedDate, Integer hit, String deleteFlag) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.memo = memo;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.hit = hit;
        this.deleteFlag = deleteFlag;
    }

    public BoardEntity toEntity() {
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .memo(memo)
                .build();
        return boardEntity;
    }
}
