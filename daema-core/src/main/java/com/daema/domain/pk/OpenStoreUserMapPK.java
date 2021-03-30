package com.daema.domain.pk;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpenStoreUserMapPK implements Serializable {

	private static final long serialVersionUID = 270442920138578680L;

	private long openStoreId;
	private long userId;

	public OpenStoreUserMapPK() {

	}

	public OpenStoreUserMapPK(long openStoreId, long userId) {
		this.openStoreId = openStoreId;
		this.userId = userId;
	}
}
