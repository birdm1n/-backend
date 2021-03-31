package com.daema.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParamDto extends PagingDto {

	@ApiModelProperty(value = "관리점 ID", example = "1")
	private int parentStoreId;

	@ApiModelProperty(value = "영업점명")
	private String saleStoreName;

	@ApiModelProperty(value = "통신사 ID", example = "1")
	private int telecom;

	@ApiModelProperty(value = "사업자 번호")
	private String bizNo;

	@ApiModelProperty(value = "담당자 연락처")
	private String chargerPhone;

	@ApiModelProperty(value = "반품 주소지")
	private String returnAddr;

	@ApiModelProperty(value = "반품 주소지 상세")
	private String returnAddrDetail;

	@ApiModelProperty(value = "사용여부")
	private String useYn;

	@ApiModelProperty(value = "조회 시작일자")
	private String srhStartDate;

	@ApiModelProperty(value = "조회 종료일자")
	private String srhEndDate;

	@ApiModelProperty(value = "상품명")
	private String goodsName;

	@ApiModelProperty(value = "모델명")
	private String modelName;

	@ApiModelProperty(value = "제조사 ID", example = "1")
	private int maker;

	@ApiModelProperty(value = "통신망 ID", example = "1")
	private int network;

	@ApiModelProperty(value = "요금제명")
	private String chargeName;

	@ApiModelProperty(value = "카테고리명")
	private String category;

	@ApiModelProperty(value = "부가서비스명")
	private String addSvcName;

	@ApiModelProperty(value = "부가서비스 요금", example = "1")
	private int addSvcCharge;
}
