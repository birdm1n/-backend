package com.daema.commgmt.domain;

import com.daema.commgmt.domain.pk.FuncRoleMapPK;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@EqualsAndHashCode(of={"funcId", "roleId"})
@IdClass(FuncRoleMapPK.class)
@ToString
@Entity
@Table(name="func_role_map")
@NoArgsConstructor
public class FuncRoleMap {

    @Id
    @Column(name = "func_id", columnDefinition = "varchar(255) comment '기능 아이디'")
    private String funcId;

    @Id
    @Column(name = "role_id", columnDefinition = "INT comment '룰 아이디'")
    private int roleId;

    @Builder
    public FuncRoleMap(String funcId, int roleId){
        this.funcId = funcId;
        this.roleId = roleId;
    }
}