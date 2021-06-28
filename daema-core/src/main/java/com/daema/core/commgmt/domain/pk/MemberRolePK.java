package com.daema.core.commgmt.domain.pk;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MemberRolePK implements Serializable {

	private static final long serialVersionUID = -2263366215448584838L;

	private long seq;
	private long roleId;

	public MemberRolePK(long seq, long roleId) {
		this.seq = seq;
		this.roleId = roleId;
	}
}
