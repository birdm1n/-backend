package com.daema.domain.pk;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FuncRoleMapPK implements Serializable {

	private static final long serialVersionUID = -3997500504328042362L;

	private String funcId;
	private int roleId;

	public FuncRoleMapPK(String funcId, int roleId) {
		this.funcId = funcId;
		this.roleId = roleId;
	}
}
