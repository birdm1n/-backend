package com.daema.dto;

import com.daema.domain.Store;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleStoreMgmtDto {

	private long storeId;
	private String saleStoreName;
	private int telecom;
	private String bizNo;
	private String chargerPhone;
	private String returnZipCode;
	private String returnAddr;
	private String returnAddrDetail;
	private String useYn;
	private LocalDateTime regiDateTime;

	public static SaleStoreMgmtDto from (Store store) {
		return SaleStoreMgmtDto.builder()
				.storeId(store.getStoreId())
				.saleStoreName(store.getStoreName())
				.telecom(store.getTelecom())
				.bizNo(store.getBizNo())
				.chargerPhone(store.getChargerPhone())
				.returnZipCode(store.getReturnZipCode())
				.returnAddr(store.getReturnAddr())
				.returnAddrDetail(store.getReturnAddrDetail())
				.useYn(store.getUseYn())
				.regiDateTime(store.getRegiDateTime())
				.build();
	}
}