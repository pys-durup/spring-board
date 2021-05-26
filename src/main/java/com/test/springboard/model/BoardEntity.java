package com.test.springboard.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tbl_board")
public class BoardEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String memo;

    @Column
    private Integer hit;

    @Column
    private String deleteFlag;

    @Builder
    public BoardEntity(Long id, String writer, String title, String memo) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.memo = memo;
        this.hit = 0;
        this.deleteFlag = "F";
    }
}
