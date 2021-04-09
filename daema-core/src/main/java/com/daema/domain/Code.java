package com.daema.domain;

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
    @Column(name = "code_id", length = 15)
    private String codeId;

    @Column(name = "code_id_nm", length = 20)
    private String codeIdNm;

    @Column(name = "code_id_desc")
    private String codeIdDesc;

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
