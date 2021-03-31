package com.daema.dto;

import com.daema.domain.AddServiceRegReq;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddServiceRegReqDto {

	private long addSvcRegReqId;
	private long reqStoreId;
	private String addSvcName;
	private int addSvcCharge;
	private int telecom;
	private LocalDateTime regiDateTime;
	private int reqStatus;
	private AddServiceRegReqRejectDto regReqRejectDto;

	public static AddServiceRegReqDto from (AddServiceRegReq addServiceRegReq) {
		return AddServiceRegReqDto.builder()
				.addSvcRegReqId(addServiceRegReq.getAddSvcRegReqId())
				.reqStoreId(addServiceRegReq.getReqStoreId())
				.addSvcName(addServiceRegReq.getAddSvcName())
				.addSvcCharge(addServiceRegReq.getAddSvcCharge())
				.telecom(addServiceRegReq.getTelecom())
				.regiDateTime(addServiceRegReq.getRegiDateTime())
				.reqStatus(addServiceRegReq.getReqStatus())
				.regReqRejectDto(AddServiceRegReqRejectDto.from(addServiceRegReq.getAddServiceRegReqReject()))
				.build();
	}
}
















