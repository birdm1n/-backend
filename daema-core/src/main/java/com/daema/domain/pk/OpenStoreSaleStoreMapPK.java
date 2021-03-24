package com.daema.domain.pk;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpenStoreSaleStoreMapPK implements Serializable {

	private static final long serialVersionUID = 5470591590923058171L;

	private long openStoreId;
	private long saleStoreId;

	public OpenStoreSaleStoreMapPK() {

	}

	public OpenStoreSaleStoreMapPK(long openStoreId, long saleStoreId) {
		this.openStoreId = openStoreId;
		this.saleStoreId = saleStoreId;
	}
}
