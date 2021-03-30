package com.daema.dto;

import com.daema.domain.OpenStore;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningStoreMgmtDto {

	private long openStoreId;
	private long storeId;
	private String openStoreName;
	private int telecom;
	private String bizNo;
	private String chargerPhone;
	private String returnZipCode;
	private String returnAddr;
	private String returnAddrDetail;
	private String useYn;
	private String delYn;
	private LocalDateTime regiDateTime;

	public static OpeningStoreMgmtDto from (OpenStore openStore) {
		return OpeningStoreMgmtDto.builder()
				.openStoreId(openStore.getOpenStoreId())
				.storeId(openStore.getStoreId())
				.openStoreName(openStore.getOpenStoreName())
				.telecom(openStore.getTelecom())
				.bizNo(openStore.getBizNo())
				.chargerPhone(openStore.getChargerPhone())
				.returnZipCode(openStore.getReturnZipCode())
				.returnAddr(openStore.getReturnAddr())
				.returnAddrDetail(openStore.getReturnAddrDetail())
				.useYn(openStore.getUseYn())
				.delYn(openStore.getDelYn())
				.regiDateTime(openStore.getRegiDateTime())
				.build();
	}
}
