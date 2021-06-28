package com.daema.core.wms.dto;

import com.daema.core.wms.domain.Provider;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderMgmtDto {

	private Long provId;
	private String provName;
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
	private Long updUserId;
	private LocalDateTime updDateTime;
	private Long regiUserId;
	private String name;
	private Long storeId;

	public static ProviderMgmtDto from (Provider provider) {
		return ProviderMgmtDto.builder()
				.provId(provider.getProvId())
				.provName(provider.getProvName())
				.chargerName(provider.getChargerName())
				.chargerEmail(provider.getChargerEmail())
				.chargerPhone(provider.getChargerPhone())
				.chargerPhone1(provider.getChargerPhone1())
				.chargerPhone2(provider.getChargerPhone2())
				.chargerPhone3(provider.getChargerPhone3())
				.returnZipCode(provider.getReturnZipCode())
				.returnAddr(provider.getReturnAddr())
				.returnAddrDetail(provider.getReturnAddrDetail())
				.useYn(provider.getUseYn())
				.regiUserId(provider.getRegiUserId())
				.regiDateTime(provider.getRegiDateTime())
				.updUserId(provider.getUpdUserId())
				.updDateTime(provider.getUpdDateTime())
				.name(provider.getName())
			.build();
	}
	public static ProviderMgmtDto ofInitData (Provider provider) {
		return ProviderMgmtDto.builder()
				.provId(provider.getProvId())
				.provName(provider.getProvName())
				.build();
	}
}