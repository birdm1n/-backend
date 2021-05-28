package com.daema.commgmt.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of="urlPath")
@ToString
@NoArgsConstructor
@Entity
@Table(name = "role_mgmt")
public class RoleMgmt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id" , columnDefinition = "int comment '역할 아이디'")
    private int roleId;

    @Column(name = "role_name" , columnDefinition = "varchar(255) comment '역할 이름'")
    private String roleName;

    @Column(nullable = false, name = "necessary_yn", columnDefinition ="char(1) comment'필수 여부'")
    private String necessaryYn= "N";

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1) comment'삭제 여부'")
    private String delYn = "N";

    @Column(name = "store_id" , columnDefinition = "BIGINT unsigned comment '관리점 아이디'")
    private long storeId;

    @Column(name = "regi_datetime", columnDefinition = "DATETIME(6) comment '등록 날짜시간'")
    private LocalDateTime regiDateTime;

    @Builder
    public RoleMgmt(int roleId, String roleName, String necessaryYn, String delYn, long storeId, LocalDateTime regiDateTime){
        this.roleId = roleId;
        this.roleName = roleName;
        this.necessaryYn = necessaryYn;
        this.delYn = delYn;
        this.storeId = storeId;
        this.regiDateTime = regiDateTime;
    }

    public void updateDelYn(RoleMgmt roleMgmt, String delYn){
        roleMgmt.setDelYn(delYn);
    }
}
