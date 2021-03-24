package com.daema.domain.pk;

import lombok.Data;

import java.io.Serializable;

@Data
public class StoreMapPK implements Serializable {

	private static final long serialVersionUID = -5593363763196227024L;

	private long storeId;
	private long parentStoreId;

	public StoreMapPK() {

	}

	public StoreMapPK(long storeId, long parentStoreId) {
		this.storeId = storeId;
		this.parentStoreId = parentStoreId;
	}
}
