package com.daema.rest.commgmt.dto;

import com.daema.commgmt.domain.ChargeRegReq;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeRegReqDto {

	private long chargeRegReqId;
	private long reqStoreId;
	private int chargeAmt;
	private String chargeName;
	private String category;
	private Long telecom;
	private Long network;
	private LocalDateTime regiDateTime;
	private String reqStatus;
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
	private ChargeRegReqRejectDto regReqRejectDto;
	private String makerName;
	private String networkName;
	private String telecomName;
	private String reqStoreName;

	public static ChargeRegReqDto from (ChargeRegReq chargeRegReq) {
		return ChargeRegReqDto.builder()
				.chargeRegReqId(chargeRegReq.getChargeRegReqId())
				.reqStoreId(chargeRegReq.getReqStoreId())
				.chargeName(chargeRegReq.getChargeName())
				.category(chargeRegReq.getCategory())
				.chargeAmt(chargeRegReq.getChargeAmt())
				.telecom(chargeRegReq.getNetworkAttribute().getTelecom())
				.network(chargeRegReq.getNetworkAttribute().getNetwork())
				.regiDateTime(chargeRegReq.getRegiDateTime())
				.reqStatus(chargeRegReq.getReqStatus())
				.voiceAmt(chargeRegReq.getVoiceAmt())
				.dataAmt(chargeRegReq.getDataAmt())
				.smsAmt(chargeRegReq.getSmsAmt())
				.videoAmt(chargeRegReq.getVideoAmt())
				.extraVoiceAmt(chargeRegReq.getExtraVoiceAmt())
				.extraDataAmt(chargeRegReq.getExtraDataAmt())
				.extraSmsAmt(chargeRegReq.getExtraSmsAmt())
				.extraVideoAmt(chargeRegReq.getExtraVideoAmt())
				.extraVideoAmt(chargeRegReq.getExtraVideoAmt())
				.extraVideoAmt(chargeRegReq.getExtraVideoAmt())
				.chargeDesc(chargeRegReq.getChargeDesc())
				.addBenefit(chargeRegReq.getAddBenefit())
				.regReqRejectDto(ChargeRegReqRejectDto.from(chargeRegReq.getChargeRegReqReject()))
				.makerName(chargeRegReq.getMakerName())
				.telecomName(chargeRegReq.getTelecomName())
				.networkName(chargeRegReq.getNetworkName())
				.reqStoreName(chargeRegReq.getReqStoreName())
			.build();
	}
}
















