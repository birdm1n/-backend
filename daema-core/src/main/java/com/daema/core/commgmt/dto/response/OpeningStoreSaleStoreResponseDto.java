package com.daema.core.commgmt.dto.response;

import com.daema.core.commgmt.domain.Store;
import com.daema.core.commgmt.dto.OpeningStoreMgmtDto;
import com.daema.core.commgmt.dto.SaleStoreMgmtDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpeningStoreSaleStoreResponseDto {

	public List<OpeningStoreMgmtDto> openStoreList;
	public List<OpenStoreSaleStoreMap> saleStoreList = new ArrayList<>();

	public static class OpenStoreSaleStoreMap{
		public SaleStoreMgmtDto saleStore;
		public List<String[]> openStoreMap;

		public OpenStoreSaleStoreMap(Store store, List<String[]> filterOpenStoreInfo) {
			saleStore = SaleStoreMgmtDto.from(store);
			openStoreMap = filterOpenStoreInfo;
		}
	}

}
