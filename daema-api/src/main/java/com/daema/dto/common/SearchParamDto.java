package com.daema.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParamDto extends PagingDto {

	@ApiModelProperty(value = "조회 시작일자")
	private String srhStartDate;

	@ApiModelProperty(value = "조회 종료일자")
	private String srhEndDate;

}
