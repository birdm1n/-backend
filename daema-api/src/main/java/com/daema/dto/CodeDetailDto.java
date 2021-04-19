package com.daema.dto;

import com.daema.domain.CodeDetail;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeDetailDto {

	private int codeSeq;
	private String codeNm;
	private String codeDesc;
	private int orderNum;
	private String codeId;

	public static CodeDetailDto ofInitData (CodeDetail codeDetail) {
		return CodeDetailDto.builder()
				.codeSeq(codeDetail.getCodeSeq())
				.codeNm(codeDetail.getCodeNm())
				.codeDesc(codeDetail.getCodeDesc())
				.orderNum(codeDetail.getOrderNum())
				.codeId(codeDetail.getCodeId())
			.build();
	}
}
