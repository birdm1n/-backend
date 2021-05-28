package com.daema.commgmt.domain;

import com.daema.commgmt.domain.pk.MemberRolePK;
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
@Table(name="members_role")
public class MemberRole {

    @Id
    @JoinColumn(name = "seq" , columnDefinition = "BIGINT unsigned comment '시퀀스'")
    private long seq;

    @Id
    @JoinColumn(name = "role_id",  columnDefinition = "int comment '역할 아이디'")
    private int roleId;

    @Builder
    public MemberRole(@NotNull long seq, @NotNull int roleId) {
        this.seq = seq;
        this.roleId = roleId;
    }
}


