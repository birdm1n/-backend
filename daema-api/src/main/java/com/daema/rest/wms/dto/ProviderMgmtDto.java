package com.daema.rest.wms.dto;

import com.daema.wms.domain.Provider;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderMgmtDto {

	private long provId;
	private String provName;
	private String chargerName;
	private String chargerEmail;
	private String chargerPhone;
	private String returnZipCode;
	private String returnAddr;
	private String returnAddrDetail;
	private String useYn;
	private LocalDateTime regiDateTime;
	private Long updUserId;
	private LocalDateTime updDateTime;
	private long regiUserId;
	private String name;
	private long storeId;

	public static ProviderMgmtDto from (Provider provider) {
		return ProviderMgmtDto.builder()
				.provId(provider.getProvId())
				.provName(provider.getProvName())
				.chargerName(provider.getChargerName())
				.chargerEmail(provider.getChargerEmail())
				.chargerPhone(provider.getChargerPhone())
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