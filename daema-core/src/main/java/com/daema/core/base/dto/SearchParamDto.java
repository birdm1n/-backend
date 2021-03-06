package com.daema.core.base.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 시스템에서 중복되는 param 정의
 */
@Getter
@Setter
public class SearchParamDto extends PagingDto {

    private long storeId;

    private String chargerName;

    private String chargerPhone;

    private String chargerPhone1;

    private String chargerPhone2;

    private String chargerPhone3;

    private String chargerEmail;

    private String returnAddr;

    private String returnAddrDetail;

    private String useYn;

    private String srhStartDate;

    private String srhEndDate;

}
