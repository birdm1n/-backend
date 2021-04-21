package com.daema.commgmt.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OpenStoreListDto {

    private int openStoreId;

    private String bizNo;

    private String chargerPhone;

    private String openStoreName;

    private LocalDateTime regiDateTime;

    private String returnAddr;

    private String returnAddrDetail;

    private String returnZipCode;

    private long storeId;

    private int telecom;

    private String useYn;

    private String delYn;

    private String telecomName;

    private long reqStoreId;

}
