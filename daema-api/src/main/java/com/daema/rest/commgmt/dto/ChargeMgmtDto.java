package com.daema.rest.commgmt.dto;

import com.daema.commgmt.domain.Charge;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeMgmtDto {

	private long chargeId;
	private String chargeName;
	private int chargeAmt;
	private String category;
	private Long telecom;
	private Long network;
	private String originKey;
	private String chargeCode;
	private String useYn;
	private String matchingYn;
	private String delYn;
	private LocalDateTime regiDateTime;
	private String voiceAmt;
	private String dataAmt;
	private String smsAmt;
	private String videoAmt;
	private String extraVoiceAmt;
	private String extraDataAmt;
	private String extraSmsAmt;
	private String extraVideoAmt;
	private String chargeDesc;
	private String addBenefit;
	private String makerName;
	private String networkName;
	private String telecomName;

	public static ChargeMgmtDto from (Charge charge) {
		return ChargeMgmtDto.builder()
				.chargeId(charge.getChargeId())
				.chargeName(charge.getChargeName())
				.category(charge.getCategory())
				.chargeAmt(charge.getChargeAmt())
				.telecom(charge.getNetworkAttribute().getTelecom())
				.network(charge.getNetworkAttribute().getNetwork())
				.originKey(charge.getOriginKey())
				.chargeCode(charge.getChargeCode())
				.regiDateTime(charge.getRegiDateTime())
				.useYn(charge.getUseYn())
				.matchingYn(charge.getMatchingYn())
				.delYn(charge.getDelYn())
				.voiceAmt(charge.getVoiceAmt())
				.dataAmt(charge.getDataAmt())
				.smsAmt(charge.getSmsAmt())
				.videoAmt(charge.getVideoAmt())
				.extraVoiceAmt(charge.getExtraVoiceAmt())
				.extraDataAmt(charge.getExtraDataAmt())
				.extraSmsAmt(charge.getExtraSmsAmt())
				.extraVideoAmt(charge.getExtraVideoAmt())
				.extraVideoAmt(charge.getExtraVideoAmt())
				.extraVideoAmt(charge.getExtraVideoAmt())
				.chargeDesc(charge.getChargeDesc())
				.addBenefit(charge.getAddBenefit())
				.makerName(charge.getMakerName())
				.telecomName(charge.getTelecomName())
				.networkName(charge.getNetworkName())
			.build();
	}
}
