package com.daema.base.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of={"codeId", "codeSeq"})
@ToString
@Entity
@org.hibernate.annotations.Table(appliesTo = "code_detail", comment = "코드 상세")
public class CodeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id", columnDefinition = "BIGINT unsigned comment '코드 아이디'")
    private Long codeSeq;

    @Column(name = "code_group", columnDefinition = "varchar(255) comment '코드 그룹'")
    private String codeId;

    @Column(name = "code_name", columnDefinition = "varchar(255) comment '코드 이름'")
    private String codeNm;

    @Column(name = "code_desc", columnDefinition = "varchar(255) comment '코드 설명'")
    private String codeDesc;

    @Column(name = "use_yn", columnDefinition ="char(1) comment '사용 여부'")
    private String useYn = "Y";

    @Column(name = "order_seq", columnDefinition = "INT comment '정렬 순번'")
    private int orderNum;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 일시'"  )
    private LocalDateTime regiDateTime;

    @Column(name = "regi_user_id", columnDefinition = "BIGINT UNSIGNED comment '등록 유저 아이디'")
    private long regiUserId;

    @Column(name = "upd_datetime", columnDefinition = "DATETIME(6) comment '수정 일시'")
    private LocalDateTime updDateTime;

    @Column(name = "upd_user_id", columnDefinition = "BIGINT UNSIGNED comment '수정 유저 아이디'")
    private Long updUserId;


}
