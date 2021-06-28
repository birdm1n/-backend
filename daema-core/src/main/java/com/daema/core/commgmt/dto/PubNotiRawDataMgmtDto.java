package com.daema.core.commgmt.dto;

import com.daema.core.commgmt.domain.PubNotiRawData;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PubNotiRawDataMgmtDto {

	private long pubNotiRawDataId;
	private String goodsName;
	private String modelName;
	private String makerName;
	private String chargeName;
	private String telecomName;
	private String networkName;
	private int releaseAmt;
	private int supportAmt;
	private LocalDate releaseDate;
	private LocalDateTime regiDateTime;
	private LocalDateTime deadLineDateTime;
	private String deadLineYn;
	private Long deadLineUserId;

	public static PubNotiRawDataMgmtDto from (PubNotiRawData pubNotiRawData) {
		return PubNotiRawDataMgmtDto.builder()
				.pubNotiRawDataId(pubNotiRawData.getPubNotiRawDataId())
				.goodsName(pubNotiRawData.getGoodsName())
				.modelName(pubNotiRawData.getModelName())
				.makerName(pubNotiRawData.getMakerName())
				.chargeName(pubNotiRawData.getChargeName())
				.telecomName(pubNotiRawData.getTelecomName())
				.networkName(pubNotiRawData.getNetworkName())
				.releaseAmt(pubNotiRawData.getReleaseAmt())
				.supportAmt(pubNotiRawData.getSupportAmt())
				.releaseDate(pubNotiRawData.getReleaseDate())
				.regiDateTime(pubNotiRawData.getRegiDateTime())
				.deadLineDateTime(pubNotiRawData.getDeadLineDateTime())
				.deadLineYn(pubNotiRawData.getDeadLineYn())
				.deadLineUserId(pubNotiRawData.getDeadLineUserId())
			.build();
	}
}
