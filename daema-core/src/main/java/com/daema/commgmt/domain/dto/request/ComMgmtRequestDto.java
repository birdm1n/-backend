package com.daema.commgmt.domain.dto.request;

import com.daema.base.dto.SearchParamDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComMgmtRequestDto extends SearchParamDto {

	private long parentStoreId;

	private String saleStoreName;

	private String openStoreName;

	private Integer[] telecom;

	private String bizNo;

	private String matchingYn;

	private String goodsName;

	private long goodsId;

	private int reqStatus;

	private String modelName;

	private int maker;

	private int network;

	private String chargeName;

	private long chargeId;

	private String category;

	private String addSvcName;

	private int addSvcCharge;

	private String name;

	private String email;

	private String phone;


}
