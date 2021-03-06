package com.daema.core.commgmt.dto;

import com.daema.core.base.enums.TypeEnum;
import com.daema.core.commgmt.domain.AddServiceRegReq;
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
	private TypeEnum.AddSvcType addSvcType;
	private String addSvcTypeName;
	private Long telecom;
	private LocalDateTime regiDateTime;
	private String reqStatus;
	private AddServiceRegReqRejectDto regReqRejectDto;
	private String telecomName;
	private String reqStoreName;

	public static AddServiceRegReqDto from (AddServiceRegReq addServiceRegReq) {
		return AddServiceRegReqDto.builder()
				.addSvcRegReqId(addServiceRegReq.getAddSvcRegReqId())
				.reqStoreId(addServiceRegReq.getReqStoreId())
				.addSvcName(addServiceRegReq.getAddSvcName())
				.addSvcCharge(addServiceRegReq.getAddSvcCharge())
				.addSvcType(addServiceRegReq.getAddSvcType())
				.telecom(addServiceRegReq.getTelecom())
				.regiDateTime(addServiceRegReq.getRegiDateTime())
				.reqStatus(addServiceRegReq.getReqStatus())
				.regReqRejectDto(AddServiceRegReqRejectDto.from(addServiceRegReq.getAddServiceRegReqReject()))
				.telecomName(addServiceRegReq.getTelecomName())
				.reqStoreName(addServiceRegReq.getReqStoreName())
			.build();
	}

	public String getAddSvcTypeName() {
		return this.addSvcType != null ? this.addSvcType.getStatusMsg() : "";
	}
}
















