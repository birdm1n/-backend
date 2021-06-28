package com.daema.core.commgmt.domain;

import com.daema.core.commgmt.domain.pk.MemberRolePK;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@EqualsAndHashCode(of={"seq", "roleId"})
@IdClass(MemberRolePK.class)
@ToString
@NoArgsConstructor
@Entity
@org.hibernate.annotations.Table(appliesTo = "members_role", comment = "회원 역할")
public class MembersRole {

    @Id
    @Column(name = "member_id", columnDefinition = "BIGINT unsigned comment '유저 아이디'")
    private long seq;

    @Id
    @Column(name = "role_id",  columnDefinition = "BIGINT unsigned comment '역할 아이디'")
    private long roleId;

    @Builder
    public MembersRole(@NotNull long seq, @NotNull long roleId) {
        this.seq = seq;
        this.roleId = roleId;
    }
}


