package com.daema.rest.commgmt.dto.request;

import com.daema.rest.commgmt.dto.OrganizationMemberDto;
import com.daema.rest.commgmt.dto.SaleStoreMgmtDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleStoreUserWrapperDto {
	private long parentStoreId;
	private SaleStoreMgmtDto saleStore;
	private OrganizationMemberDto member;
}
