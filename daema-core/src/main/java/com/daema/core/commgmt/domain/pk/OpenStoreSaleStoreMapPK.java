package com.daema.core.commgmt.domain.pk;

import lombok.Data;

import java.io.Serializable;

@Data
public class OpenStoreSaleStoreMapPK implements Serializable {

	private static final long serialVersionUID = 5470591590923058171L;

	private long openingStoreId;
	private long saleStoreId;

	public OpenStoreSaleStoreMapPK() {

	}

	public OpenStoreSaleStoreMapPK(long openingStoreId, long saleStoreId) {
		this.openingStoreId = openingStoreId;
		this.saleStoreId = saleStoreId;
	}
}
