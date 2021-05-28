package com.daema.base.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="codeId")
@ToString
@NoArgsConstructor
@Entity
@Table(name = "code")
public class Code {

    @Id
    @Column(name = "code_group", length = 15, columnDefinition = "varchar(255) comment '코드 아이디'")
    private String codeId;

    @Column(name = "code_group_name", length = 20, columnDefinition = "varchar(255) comment '코드 아이디 이름'")
    private String codeIdNm;

    @Column(name = "code_group_desc", columnDefinition = "varchar(255) comment '코드 아이디 설명'")
    private String codeIdDesc;

    @Column(name = "use_yn", columnDefinition ="char(1) comment '사용 여부'")
    private String useYn = "Y";

    @Column(name = "order_num", columnDefinition = "INT comment '순서 넘버'")
    private int orderNum;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 날짜시간'")
    private LocalDateTime regiDateTime;

    @Column(name = "regi_user_id", columnDefinition = "BIGINT UNSIGNED comment '등록 유저 아이디'")
    private long regiUserId;

    @Column(name = "upd_datetime", columnDefinition = "DATETIME(6) comment '업데이트 날짜시간'")
    private LocalDateTime updDateTime;

    @Column(name = "upd_user_id", columnDefinition = "BIGINT UNSIGNED comment '업데이트 유저 아이디'")
    private Long updUserId;
}
