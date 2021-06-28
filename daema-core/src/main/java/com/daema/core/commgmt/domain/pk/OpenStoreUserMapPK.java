package com.daema.core.commgmt.domain.pk;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpenStoreUserMapPK implements Serializable {

	private static final long serialVersionUID = 270442920138578680L;

	private long openingStoreId;
	private long userId;

	public OpenStoreUserMapPK() {

	}

	public OpenStoreUserMapPK(long openingStoreId, long userId) {
		this.openingStoreId = openingStoreId;
		this.userId = userId;
	}
}
