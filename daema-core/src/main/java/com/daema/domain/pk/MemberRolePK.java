package com.daema.domain.pk;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MemberRolePK implements Serializable {

	private static final long serialVersionUID = -2263366215448584838L;

	private long seq;
	private int roleId;

	public MemberRolePK(long seq, int roleId) {
		this.seq = seq;
		this.roleId = roleId;
	}
}
