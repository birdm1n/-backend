package com.daema.core.commgmt.dto;

import com.daema.core.commgmt.domain.OpenStore;
import com.daema.core.commgmt.dto.response.OpenStoreListDto;
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
	private Long telecom;
	private String telecomName;
	private String bizNo;
	private String chargerPhone;
	private String chargerPhone1;
	private String chargerPhone2;
	private String chargerPhone3;
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
				.chargerPhone1(openStore.getChargerPhone1())
				.chargerPhone2(openStore.getChargerPhone2())
				.chargerPhone3(openStore.getChargerPhone3())
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
				.chargerPhone1(openStoreListDto.getChargerPhone1())
				.chargerPhone2(openStoreListDto.getChargerPhone2())
				.chargerPhone3(openStoreListDto.getChargerPhone3())
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
