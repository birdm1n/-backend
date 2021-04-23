package com.daema.rest.commgmt.dto;

import com.daema.commgmt.domain.OpenStore;
import com.daema.commgmt.domain.dto.response.OpenStoreListDto;
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
	private String telecomName;
	private String bizNo;
	private String chargerPhone;
	private String chargerName;
	private String returnZipCode;
	private String returnAddr;
	private String returnAddrDetail;
	private String useYn;
	private String delYn;
	private LocalDateTime regiDateTime;
	private long reqStoreId;

	public static OpeningStoreMgmtDto from (OpenStore openStore) {
		return OpeningStoreMgmtDto.builder()
				.openStoreId(openStore.getOpenStoreId())
				.storeId(openStore.getStoreId())
				.openStoreName(openStore.getOpenStoreName())
				.telecom(openStore.getTelecom())
				.telecomName(openStore.getTelecomName())
				.bizNo(openStore.getBizNo())
				.chargerName(openStore.getChargerName())
				.chargerPhone(openStore.getChargerPhone())
				.returnZipCode(openStore.getReturnZipCode())
				.returnAddr(openStore.getReturnAddr())
				.returnAddrDetail(openStore.getReturnAddrDetail())
				.useYn(openStore.getUseYn())
				.delYn(openStore.getDelYn())
				.regiDateTime(openStore.getRegiDateTime())
			.build();
	}

	public static OpeningStoreMgmtDto dtoToDto (OpenStoreListDto openStoreListDto) {
		return OpeningStoreMgmtDto.builder()
				.openStoreId(openStoreListDto.getOpenStoreId())
				.storeId(openStoreListDto.getStoreId())
				.openStoreName(openStoreListDto.getOpenStoreName())
				.telecom(openStoreListDto.getTelecom())
				.telecomName(openStoreListDto.getTelecomName())
				.bizNo(openStoreListDto.getBizNo())
				.chargerName(openStoreListDto.getChargerName())
				.chargerPhone(openStoreListDto.getChargerPhone())
				.returnZipCode(openStoreListDto.getReturnZipCode())
				.returnAddr(openStoreListDto.getReturnAddr())
				.returnAddrDetail(openStoreListDto.getReturnAddrDetail())
				.useYn(openStoreListDto.getUseYn())
				.delYn(openStoreListDto.getDelYn())
				.regiDateTime(openStoreListDto.getRegiDateTime())
				.reqStoreId(openStoreListDto.getReqStoreId())
			.build();
	}
}
