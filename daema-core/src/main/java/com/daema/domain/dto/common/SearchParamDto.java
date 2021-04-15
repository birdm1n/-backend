package com.daema.domain.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchParamDto extends PagingDto {

	private long parentStoreId;

	private long storeId;

	private String saleStoreName;

	private String openStoreName;

	private Integer[] telecom;

	private String bizNo;

	private String chargerPhone;

	private String returnAddr;

	private String returnAddrDetail;

	private String useYn;

	private String srhStartDate;

	private String srhEndDate;

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
