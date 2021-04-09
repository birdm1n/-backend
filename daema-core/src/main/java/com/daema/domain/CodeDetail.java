package com.daema.domain;

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
@Table(name = "code_detail")
public class CodeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_seq")
    private int codeSeq;

    @Column(name = "code_id", length = 15)
    private String codeId;

    @Column(name = "code_nm")
    private String codeNm;

    @Column(name = "code_desc")
    private String codeDesc;

    @Column(name = "use_yn", columnDefinition ="char(1)")
    private String useYn = "Y";

    @Column(name = "order_num")
    private int orderNum;

    @Column(name = "regi_datetime")
    private LocalDateTime regiDateTime;

    @Column(name = "regi_user_id")
    private long regiUserId;

    @Column(name = "last_upd_datetime")
    private LocalDateTime lastUpdDateTime;

    @Column(name = "last_upd_user_id")
    private long lastUpdUserId;


}
