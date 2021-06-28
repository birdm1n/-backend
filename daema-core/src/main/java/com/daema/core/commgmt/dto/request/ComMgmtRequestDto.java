package com.daema.core.commgmt.dto.request;

import com.daema.core.base.dto.SearchParamDto;
import com.daema.core.base.enums.TypeEnum;
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

	private Integer reqStatus;

	private String modelName;

	private Long maker;

	private Long network;

	private String chargeName;

	private long chargeId;

	private String category;

	private String addSvcName;

	private TypeEnum.AddSvcType addSvcType;

	private int addSvcCharge;

	private String name;

	private String email;

	private String phone;

	private String phone1;

	private String phone2;

	private String phone3;


}
