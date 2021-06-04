package com.daema.rest.commgmt.dto;

import com.daema.commgmt.domain.Store;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {

	private long storeId;
	private String storeName;
	private Long telecom;
	private String telecomName;
	private String bizNo;
	private String ceoName;
	private String chargerName;
	private String chargerEmail;
	private String chargerPhone;
	private String chargerPhone1;
	private String chargerPhone2;
	private String chargerPhone3;
	private String returnZipCode;
	private String returnAddr;
	private String returnAddrDetail;
	private String useYn;
	private LocalDateTime regiDateTime;

	public static StoreDto from (Store store) {
		return StoreDto.builder()
				.storeId(store.getStoreId())
				.storeName(store.getStoreName())
				.telecom(store.getTelecom())
				.telecomName(store.getTelecomName())
				.bizNo(store.getBizNo())
				.ceoName(store.getCeoName())
				.chargerPhone(store.getChargerPhone())
				.chargerPhone1(store.getChargerPhone1())
				.chargerPhone2(store.getChargerPhone2())
				.chargerPhone3(store.getChargerPhone3())
				.chargerName(store.getChargerName())
				.chargerEmail(store.getChargerEmail())
				.returnZipCode(store.getReturnZipCode())
				.returnAddr(store.getReturnAddr())
				.returnAddrDetail(store.getReturnAddrDetail())
				.useYn(store.getUseYn())
				.regiDateTime(store.getRegiDateTime())
			.build();
	}
}