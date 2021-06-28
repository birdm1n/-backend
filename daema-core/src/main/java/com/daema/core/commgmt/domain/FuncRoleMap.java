package com.daema.core.commgmt.domain;

import com.daema.core.commgmt.domain.pk.FuncRoleMapPK;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@EqualsAndHashCode(of={"funcId", "roleId", "storeId"})
@IdClass(FuncRoleMapPK.class)
@ToString
@Entity
@org.hibernate.annotations.Table(appliesTo = "func_role_map", comment = "기능 역할 맵핑")
@NoArgsConstructor
public class FuncRoleMap {

    @Id
    @Column(name = "func_name", columnDefinition = "varchar(255) comment '기능 이름'")
    private String funcId;

    @Id
    @Column(name = "role_id", columnDefinition = "BIGINT unsigned comment '역할 아이디'")
    private long roleId;

    @Id
    @Column(name = "store_id", columnDefinition = "BIGINT UNSIGNED comment '관리점 아이디'")
    private Long storeId;

    @Builder
    public FuncRoleMap(String funcId, long roleId, Long storeId){
        this.funcId = funcId;
        this.roleId = roleId;
        this.storeId = storeId;
    }
}