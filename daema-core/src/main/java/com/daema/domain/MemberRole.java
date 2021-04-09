package com.daema.domain;

import com.daema.domain.pk.MemberRolePK;
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
    @JoinColumn(name = "seq")
    private long seq;

    @Id
    @JoinColumn(name = "role_id")
    private int roleId;

    @Builder
    public MemberRole(@NotNull long seq, @NotNull int roleId) {
        this.seq = seq;
        this.roleId = roleId;
    }
}


