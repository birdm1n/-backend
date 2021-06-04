package com.daema.commgmt.domain.pk;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FuncRoleMapPK implements Serializable {

	private static final long serialVersionUID = -3997500504328042362L;

	private String funcId;
	private Long roleId;
	private Long storeId;

	public FuncRoleMapPK(String funcId, Long roleId, Long storeId) {
		this.funcId = funcId;
		this.roleId = roleId;
		this.storeId = storeId;
	}
}
