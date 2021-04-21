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
    @Column(name = "func_id")
    private String funcId;

    @Id
    @Column(name = "role_id")
    private int roleId;

    @Builder
    public FuncRoleMap(String funcId, int roleId){
        this.funcId = funcId;
        this.roleId = roleId;
    }
}