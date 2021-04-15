package com.daema.dto;

import com.daema.domain.AddService;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddServiceMgmtDto {

	private long addSvcId;
	private String addSvcName;
	private int addSvcCharge;
	private int telecom;
	private int network;
	private String originKey;
	private String addSvcMemo;
	private String useYn;
	private String delYn;
	private LocalDateTime regiDateTime;
	private String telecomName;

	public static AddServiceMgmtDto from (AddService addService) {
		return AddServiceMgmtDto.builder()
				.addSvcId(addService.getAddSvcId())
				.addSvcName(addService.getAddSvcName())
				.addSvcCharge(addService.getAddSvcCharge())
				.telecom(addService.getTelecom())
				.originKey(addService.getOriginKey())
				.addSvcMemo(addService.getAddSvcMemo())
				.regiDateTime(addService.getRegiDateTime())
				.useYn(addService.getUseYn())
				.delYn(addService.getDelYn())
				.telecomName(addService.getTelecomName())
			.build();
	}
}
