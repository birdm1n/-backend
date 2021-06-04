package com.daema.base.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="codeId")
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "code", comment = "코드")
public class Code {

    @Id
    @Column(name = "code_group", columnDefinition = "varchar(255) comment '코드 그룹'")
    private String codeId;

    @Column(name = "code_group_name", columnDefinition = "varchar(255) comment '코드 그룹 이름'")
    private String codeIdNm;

    @Column(name = "code_group_desc", columnDefinition = "varchar(255) comment '코드 그룹 설명'")
    private String codeIdDesc;

    @Column(name = "use_yn", columnDefinition ="char(1) comment '사용 여부'")
    private String useYn = "Y";

    @Column(name = "order_seq", columnDefinition = "INT comment '정렬 순번'")
    private int orderNum;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'")
    private LocalDateTime regiDateTime;

    @Column(name = "regi_user_id", columnDefinition = "BIGINT UNSIGNED comment '등록 유저 아이디'")
    private long regiUserId;

    @Column(name = "upd_datetime", columnDefinition = "DATETIME(6) comment '수정 일시'")
    private LocalDateTime updDateTime;

    @Column(name = "upd_user_id", columnDefinition = "BIGINT UNSIGNED comment '수정 유저 아이디'")
    private Long updUserId;
}
