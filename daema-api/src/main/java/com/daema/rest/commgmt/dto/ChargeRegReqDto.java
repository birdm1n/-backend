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
	private int telecom;
	private int network;
	private LocalDateTime regiDateTime;
	private int reqStatus;
	private String voiceAmt;
	private String dataAmt;
	private String smsAmt;
	private Integer discountAmt;
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
				.discountAmt(chargeRegReq.getDiscountAmt())
				.regReqRejectDto(ChargeRegReqRejectDto.from(chargeRegReq.getChargeRegReqReject()))
				.makerName(chargeRegReq.getMakerName())
				.telecomName(chargeRegReq.getTelecomName())
				.networkName(chargeRegReq.getNetworkName())
				.reqStoreName(chargeRegReq.getReqStoreName())
			.build();
	}
}
















