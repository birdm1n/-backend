package com.daema.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleStoreUserWrapperDto {
	private long parentStoreId;
	private SaleStoreMgmtDto saleStore;
	private UserMgmtDto user;
}
