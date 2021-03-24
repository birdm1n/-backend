package com.daema.dto;

import com.daema.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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


	public OpeningStoreSaleStoreResponseDto() {

	}
}
