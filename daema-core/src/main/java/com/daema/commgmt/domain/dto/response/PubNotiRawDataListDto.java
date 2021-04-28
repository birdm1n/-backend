package com.daema.commgmt.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PubNotiRawDataListDto {

    private long pubNotiRawDataId;
    private LocalDateTime regiDateTime;
    private int releaseAmt;
    private LocalDate releaseDate;
    private int supportAmt;
    private String chargeName;
    private LocalDateTime deadLineDateTime;
    private Long deadLineUserId;
    private String deadLineYn;
    private String goodsName;
    private String makerName;
    private String modelName;
    private String telecomName;
    private String networkName;
    private String chargeCode;
    private Integer diffReleaseAmt;
    private Integer diffSupportAmt;
    private LocalDate prevReleaseDate;
    private Integer prevReleaseAmt;


}
