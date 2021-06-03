package com.test.springboard.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDTO {

    private Long id;
    private String username;
    private String password;

    @Builder
    public MemberDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public MemberEntity toEntity() {
        MemberEntity memberEntity = MemberEntity.builder()
                .username(username)
                .password(password)
                .build();
        return memberEntity;
    }



}
