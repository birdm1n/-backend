package com.daema.core.commgmt.dto.request;

import com.daema.core.commgmt.dto.OrganizationMemberDto;
import com.daema.core.commgmt.dto.SaleStoreMgmtDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleStoreUserWrapperDto {
	private long parentStoreId;
	private SaleStoreMgmtDto saleStore;
	private OrganizationMemberDto member;
}
