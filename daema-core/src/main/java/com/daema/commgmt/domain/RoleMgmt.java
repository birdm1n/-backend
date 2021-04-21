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
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(nullable = false, name = "necessary_yn", columnDefinition ="char(1)")
    private String necessaryYn= "N";

    @Column(nullable = false, name = "del_yn", columnDefinition ="char(1)")
    private String delYn = "N";

    @Column(name = "store_id")
    private long storeId;

    @Column(name = "regi_datetime")
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
